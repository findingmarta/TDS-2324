package com.ruirua.sampleguideapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.viewModel.HistoryViewModel;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;

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

    LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 30000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(500)
            .setMaxUpdateDelayMillis(1000)
            .build();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start user's preferences
        super.onStartCommand(intent, flags, startId);
        sp = getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);

        // Get trail's points
        points = (ArrayList<Point>) intent.getSerializableExtra("points");
        assert points != null;

        // Access the history
        PointViewModel hvm = new ViewModelProvider((ViewModelStoreOwner) this).get(PointViewModel.class);
        hvm.getTrailPoints(trail_id).observe(this, pointslist -> {
            points = new ArrayList<>(pointslist);
            Log.e("AAAAAAAAAAAAAAAAAAAAAA",points.get(0).getPoint_name());
        });

        boolean start_request = intent.getBooleanExtra("start", true);
        if (start_request) {
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, locationListener);

            // Create a notification to start a foreground service
            Notification notification = new NotificationCompat.Builder(this, "notifyChannel")
                    .setContentTitle("Foreground Service")
                    .setContentText("Running...")
                    .build();

            // Start foreground service
            startForeground(1, notification);
            Log.d("Notification Service", "Foreground Service running...");
        } else {
            // Send data to the activity
            sendData();

            // Stop the Notification Service
            stopForeground(true);
            stopSelf();
        }

        // If the service is eliminated by the system, it will not be re-created
        return START_NOT_STICKY;
    }

    public void getPreferences(){
        notification_state = sp.getBoolean("notification_state",true);  // default on
        notification_distance = sp.getInt("notification_distance", 50); // default 50 meters
    }

    private void sendData(){
        // Send current_time in order to calculate the travelled time
        Date date = new Date(System.currentTimeMillis());

        Intent intent_data = new Intent("data-event");
        intent_data.putExtra("travelled_distance",travelled_distance);
        intent_data.putExtra("stopped_time",date);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent_data);
    }

    private void sendCoords(String lat, String lng){
        Intent intent_coords = new Intent("coords-event");
        intent_coords.putExtra("current_lat",lat);
        intent_coords.putExtra("current_lng",lng);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent_coords);    // TODO talvez tenha que meter uma flag para não fazer isto muitas vezes
    }

    public void createNotification(Point point){
        // TODO Notification channel??????????????

        // Create an explicit intent for an Activity in your app.
        Intent intent = new Intent(this, PointActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID)
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
        }
        prev_location = current_location;

        Log.e("AAAAAAAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAA");


        // Check if the notifications are on
        if (notification_state){
            for (Point p : points){
                // Check if the point was already visited
                //History_Point historyPoint = hvm.checkHistoryPoint(p.getPointId());

                //if (historyPoint == null) {
                    // Create o Location using the point's coords
                    Log.e(p.getPoint_name(),p.getVisited()+" ");
                    Location point_location = new Location("point_location");
                    point_location.setLatitude(p.getPoint_lat());
                    point_location.setLatitude(p.getPoint_lng());

                    // Calculate the distance
                    float distance = current_location.distanceTo(point_location);
                    if (distance <= notification_distance) {
                        // Create notification
                        createNotification(p);
                        NOTIFICATION_ID++;
                    }
                //}
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

        // Remove location updates in order to avoid unnecessary consumption
        locationManager.removeUpdates(locationListener);
    }

}