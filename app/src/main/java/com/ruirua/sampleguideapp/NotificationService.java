package com.ruirua.sampleguideapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class NotificationService extends Service {
    private SharedPreferences sp;
    private Boolean notification_state;
    private  int notification_distance;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO AAAAAAAAA

        // Get user's preferences
        sp = getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);
        getPreferences();



        // If the service is eliminated by the system, it will not be re-created
        return START_NOT_STICKY;
    }

    public void getPreferences(){
        notification_state = sp.getBoolean("notification_state",true);  // default on
        notification_distance = sp.getInt("notification_distance", 50); // default 50 meters
    }

    public void createNotification(){

    }

    public void checkDistance(){

    }

    public void checkPermissions(){

    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

}