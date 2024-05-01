package com.ruirua.sampleguideapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;

import java.util.ArrayList;
import java.util.Date;

public class NotificationService extends LifecycleService {
    private ArrayList<Point> points;
    private SharedPreferences sp;
    private Boolean notification_state;
    private int notification_distance;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location prev_location;
    private float travelled_distance;
    private int NOTIFICATION_ID;
    private boolean sent = false;
    private Date date_start;

    @Override
    public IBinder onBind(@NonNull Intent intent) {
        // We don't provide binding, so return null
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start user's preferences
        super.onStartCommand(intent, flags, startId);
        sp = getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);

        boolean start_request = intent.getBooleanExtra("start", false);
        if (start_request) {
            date_start = new Date(System.currentTimeMillis());
            travelled_distance = 0;

            // Get trail's id
            int trail_id = intent.getIntExtra("trail_id", -1);
            assert trail_id != -1;

            // Get trail's points
            points = (ArrayList<Point>) intent.getSerializableExtra("points");
            assert points != null;

            // Access the history
            TrailViewModel tvm = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TrailViewModel.class);
            tvm.getTrailPoints(trail_id).observe(this, pointslist -> {
                points = new ArrayList<>(pointslist);
            });
            // Get Actual Location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    // Get current coordinates
                    String lat = String.valueOf(location.getLatitude());
                    String lng = String.valueOf(location.getLongitude());

                    // Send them to the activity once
                    if (!sent) {
                        Log.d("Sender", "Sending a message to the activity");
                        sendCoords(lat, lng);
                        sent = true;
                    }

                    // Check the distances
                    checkDistance(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                }
            };

            // Necessary in order to use the requestLocationUpdates
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            // Triggers the LocationListener
            // The user will receive a notification at least every 30 seconds and if the device moves more than 100 meters
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 100, locationListener);

            // Create a notification to start a foreground service
            Notification notification = new NotificationCompat.Builder(this, "foreground_channel")
                    .setContentTitle("Foreground Service")
                    .setContentText("Running...")
                    .build();

            // Start foreground service
            startForeground(1, notification);
            Log.d("Location Service", "Foreground Service running...");
        } else {
            // Send data to the activity
            sendData();

            // Stop the Foreground Service
            stopForeground(true);
            stopSelf();
        }

        // If the service is eliminated by the system, it will not be re-created
        return START_NOT_STICKY;
    }

    public void getPreferences(){
        notification_state = sp.getBoolean("notification_state",true);  // default on
        notification_distance = sp.getInt("notification_distance", 500); // default 500 meters
    }

    private void sendData(){
        // Send current_time in order to calculate the travelled time
        Date date_end = new Date(System.currentTimeMillis());

        Intent intent_data = new Intent("data-event");
        intent_data.putExtra("travelled_distance",travelled_distance);
        intent_data.putExtra("started_time",date_start);
        intent_data.putExtra("stopped_time",date_end);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent_data);
    }

    private void sendCoords(String lat, String lng){
        Intent intent_coords = new Intent("coords-event");
        intent_coords.putExtra("current_lat",lat);
        intent_coords.putExtra("current_lng",lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent_coords);
    }

    @SuppressLint("ObsoleteSdkInt")
    public void createNotification(Point point){
        // Create the notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notification_service", "Notification Channel", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create an explicit intent for an Activity in your app.
        Intent intent = new Intent(this, PointActivity.class);
        intent.putExtra("point_id",point.getPointId());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification_service")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(point.getPoint_name() + " is near you!")
                .setContentText("The distance between you and " + point.getPoint_name() + " is " + notification_distance + "m.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that fires when the user taps the notification.
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Notify - notificationId is a unique int for each notification.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void checkDistance(Location current_location){
        // Get user's preferences
        getPreferences();

        // For every location update, add the distance to the travelled distance
        if (prev_location != null) {
            travelled_distance += current_location.distanceTo(prev_location);
            Log.d("Notification Service", "Travelled Distance: " + travelled_distance);
        }
        prev_location = current_location;

        // Check if the notifications are on
        if (notification_state){
            for (Point p : points){
                // Check if the point was already visited
                if (!p.getVisited()) {
                    // Create o Location using the point's coords
                    Location point_location = new Location("point_location");
                    point_location.setLatitude(p.getPoint_lat());
                    point_location.setLongitude(p.getPoint_lng());

                    // Calculate the distance
                    float distance = current_location.distanceTo(point_location);
                    Log.d("Notification Service", "Distance to " + p.getPoint_name() + ": " + distance + " ; Preference: " + notification_distance);
                    if (distance <= notification_distance) {
                        // Create notification
                        createNotification(p);
                        NOTIFICATION_ID++;
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Remove location updates in order to avoid unnecessary consumption
        locationManager.removeUpdates(locationListener);
    }

}