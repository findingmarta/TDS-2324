package com.ruirua.sampleguideapp;

import static androidx.core.app.ActivityCompat.requestPermissions;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.viewModel.HistoryViewModel;

import java.util.ArrayList;
import java.util.Date;

public class NotificationService extends Service {
    private ArrayList<Point> points;
    private SharedPreferences sp;
    private Boolean notification_state;
    private  int notification_distance;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location prev_location;
    private Float travelled_distance;
    private static final int PERMISSION_FINE_LOCATION = 99;

    LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 30000)
            .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(500)
                .setMaxUpdateDelayMillis(1000)
                .build();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start user's preferences
        sp = getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);

        // Get trail's points
        points = (ArrayList<Point>) intent.getSerializableExtra("points");
        boolean start_request = intent.getBooleanExtra("start",true);

        if (start_request){
            // Get Actual Location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = location -> {
                // Get current coordinates
                String lat = String.valueOf(location.getLatitude());
                String lng  = String.valueOf(location.getLongitude());

                // Send them to the activity
                Log.d("Sender", "Sending a message to the activity.");

                Intent intent_coords = new Intent("coords-event");
                intent_coords.putExtra("current_lat",lat);
                intent_coords.putExtra("current_lng",lng);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent_coords);    // TODO talvez tenha que meter uma flag para não fazer isto muitas vezes

                // Check the distances
                checkDistance(location);
            };
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

    public void createNotification(Point point){
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
    }

    public void checkDistance(Location current_location){
        // Get user's preferences
        getPreferences();

        // For every location update, add the distance to the travelled distance
        if (prev_location != null) {
            travelled_distance += current_location.distanceTo(prev_location);
        }
        prev_location = current_location;

        // Access the history
        HistoryViewModel hvm = new ViewModelProvider((ViewModelStoreOwner) this).get(HistoryViewModel.class);

        // Check if the notifications are on
        if (notification_state){
            for (Point p : points){
                // Check if the point was already visited
                History_Point historyPoint = hvm.checkHistoryPoint(p.getPointId());

                if (historyPoint == null) {
                    // Create o Location using the point's coords
                    Location point_location = new Location("point_location");
                    point_location.setLatitude(p.getPoint_lat());
                    point_location.setLatitude(p.getPoint_lng());

                    // Calculate the distance
                    float distance = current_location.distanceTo(point_location);
                    if (distance <= notification_distance) {
                        // Create notification
                        createNotification(p);

                        // Check permissions

                        // Send notification
                        //notify();
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();

        // Remove location updates in order to avoid unnecessary consumption
        locationManager.removeUpdates((android.location.LocationListener) locationListener);
    }

}