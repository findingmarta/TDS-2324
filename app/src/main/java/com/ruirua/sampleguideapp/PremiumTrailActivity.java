package com.ruirua.sampleguideapp;


import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.ruirua.sampleguideapp.adapters.PointsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailWith;
import com.ruirua.sampleguideapp.viewModel.HistoryViewModel;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PremiumTrailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private SharedPreferences sp;
    private PointsRecyclerViewAdapter adapter;
    private int trail_id;
    private Trail trail;
    private TextView trail_name;
    private TextView trail_duration;
    private TextView trail_difficulty;
    private TextView trail_desc;
    private ImageView trail_image;
    private Button start_button;
    private Button stop_button;

    private MapView trail_map;

    private ArrayList<Point> points;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private LocationRequest locationRequest;
    private static final int REQUEST_CHECK_SETTING = 2;
    int PERMISSION_ID = 44;
    private Date date_start;
    private Date date_end;
    int travelled_distance;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_premium);

        // Set up the Points of Interest RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_premium_points);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // Bind trail's views
        trail_name = findViewById(R.id.premium_trail_name);
        trail_duration = findViewById(R.id.premium_trail_duration);
        trail_difficulty = findViewById(R.id.premium_trail_difficulty);
        trail_desc = findViewById(R.id.premium_trail_desc);
        trail_image = findViewById(R.id.premium_trail_gallery);
        start_button = findViewById(R.id.start_button);
        stop_button = findViewById(R.id.stop_button);


        // Register an observer to receive data from the notification service
        LocalBroadcastManager.getInstance(this).registerReceiver(coordsReceiver, new IntentFilter("coords-event"));

        // Get Permissions
        getPermissions();

        // Get trail's id
        Intent intent = getIntent();
        trail_id = intent.getIntExtra("trail_id",0);

        // Check which trail is the user navegating
        sp = getSharedPreferences("BraGuia Shared Preferences", MODE_PRIVATE);
        int trail_running = sp.getInt("trail_running",-1);

        Log.e("AAAAAAAAAAAAAAAAAAAAAAA", "Trail ID: " + trail_id + " Trail RUNNING: " + trail_running);

        // Check if I have a Notification Service running
        if(isServiceRunning(NotificationService.class)){
            stop_button.setEnabled(trail_running == -1 || trail_running == trail_id);
            start_button.setEnabled(false);
        }else{
            stop_button.setEnabled(false);
        }

        // Get the info from de database
        // Trail
        TrailViewModel tvm = new ViewModelProvider(this).get(TrailViewModel.class);



        // Given the ID initialize the trail and set its info
        tvm.setTrailViewModel(trail_id);
        LiveData<TrailWith> trailData = tvm.getTrailWith();
        trailData.observe(this, new_trail -> {
            if (new_trail != null) {
                trail = new_trail.getTrail();
                setTrailInfo();
            }
        });

        // Set up the Map
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        trail_map = findViewById(R.id.premium_mapView);
        trail_map.onCreate(mapViewBundle);


        // Trail's Points
        LiveData<List<Point>> pointsData = tvm.getTrailPoints(trail_id);
        pointsData.observe(this, pointslist -> {
            points = new ArrayList<>(pointslist);
            adapter = new PointsRecyclerViewAdapter(points,this);
            recyclerView.setAdapter(adapter);

            setStartStop();

            trail_map.getMapAsync(this);
        });
    }

    public void setTrailInfo(){
        trail_name.setText(trail.getTrail_name().toUpperCase(Locale.ROOT));
        trail_duration.setText(String.valueOf(trail.getTrail_duration()));
        trail_difficulty.setText(trail.getTrail_difficulty());
        trail_desc.setText(trail.getTrail_desc());
        Picasso.get()
                .load(trail.getTrail_image().replace("http:", "https:"))
                .into(trail_image);
    }

    public void setStartStop(){

        start_button.setOnClickListener(view -> {
            stop_button.setEnabled(true);
            start_button.setEnabled(false);
            startService();

            // Set the trail's state as running
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("trail_running",trail_id);
            editor.apply();
        });

        stop_button.setOnClickListener(view -> {
            stop_button.setEnabled(false);
            start_button.setEnabled(true);
            stopService();

            // Remove the trail's state
            SharedPreferences.Editor editor = sp.edit();
            editor.remove("trail_running");
            editor.apply();
        });
    }

    public void createPathOnMaps(String lat,String lng){
        // Create trail's link
        StringBuilder linkMaps = new StringBuilder();
        String link = "https://www.google.com/maps/dir";
        linkMaps.append(link);
        linkMaps.append("/").append(lat).append(",").append(lng);
        for(Point p:points){
            linkMaps.append("/").append(p.getPoint_lat()).append(",").append(p.getPoint_lng());
        }
        Uri uri = Uri.parse(linkMaps.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        if(points!=null) {
            for (Point p : points) {
                LatLng trail = new LatLng(p.getPoint_lat(), p.getPoint_lng());
                googleMap.addMarker(new MarkerOptions()
                        .position(trail)
                        .title(p.getPoint_name()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trail, 12));
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        trail_map.onSaveInstanceState(mapViewBundle);
    }

    public boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void startService(){
        // In this case we need a foreground service
        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra("points", points);
        serviceIntent.putExtra("trail_id", trail_id);
        serviceIntent.putExtra("start",true);

        ComponentName componentName = this.startForegroundService(serviceIntent);
        // Check if the service is running
        if (componentName != null) {
            Log.d("Notification Service", "Notification Service started...");
        } else {
            Log.e("Notification Service", "Something went wrong: Failed to start service.");
        }
    }

    public void stopService(){
        // Send another request to the Notification Service
        Intent serviceIntent = new Intent(this, NotificationService.class);
        this.startForegroundService(serviceIntent);
        Log.d("Notification Service", "Notification Service is not longer running.");
    }

    private final BroadcastReceiver coordsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.d("Coords Receiver", "Got message from the Notification Service");

            String lat = intent.getStringExtra("current_lat");
            String lng = intent.getStringExtra("current_lng");

            // Create the path on Google Maps
            createPathOnMaps(lat,lng);
        }
    };

    private final BroadcastReceiver dataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Log.d("Data Receiver", "Got message from the Notification Service");

            travelled_distance = (int) intent.getFloatExtra("travelled_distance",0);
            date_start = (Date) Objects.requireNonNull(intent.getExtras()).get("started_time");
            date_end = (Date) Objects.requireNonNull(intent.getExtras()).get("stopped_time");

            // Update trails history
            updateHistory();
        }
    };

    private void updateHistory(){
        // Calculate the time that it took to travel
        long time_difference = date_end.getTime() - date_start.getTime();
        int travelled_time = (int) TimeUnit.MILLISECONDS.toMinutes(time_difference);

        // Check if the trail is in the history
        HistoryViewModel hvm = new ViewModelProvider(this).get(HistoryViewModel.class);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            // Check if the trail is already in the history
            History_Trail historyTrail = hvm.checkHistoryTrail(trail_id);
            if (historyTrail != null){
                // Update trail if it exists in the history
                hvm.updateHistoryTrail(trail.getTrailId(),date_start,travelled_time,travelled_distance);
                // Update UI on the main thread
                runOnUiThread(() -> {
                    Toast.makeText(PremiumTrailActivity.this, "Trail's history updated!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Insert trail if it doesn't exist in the history
                hvm.insertHistoryTrail(trail.getTrailId(),date_start,travelled_time,travelled_distance);
                // Update UI on the main thread
                runOnUiThread(() -> {
                    Toast.makeText(PremiumTrailActivity.this, "Trail added to your history!", Toast.LENGTH_SHORT).show();
                });
            }
        });
        // Shutdown executor after use
        executor.shutdown();
    }

    private void getPermissions(){
        checkPermissions();
        if (!isGPSEnabled()){
            turnOnGPS();
        }
    }

    private void checkPermissions(){
        // Exact Location
        boolean hasFinePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        // Approximate Location
        boolean hasCoarsePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean hasPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            boolean hasBackgroundPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
            hasPermission = hasCoarsePermission && hasFinePermission && hasBackgroundPermission;
        } else {
            hasPermission = hasCoarsePermission && hasFinePermission;
        }

        if (!hasPermission){
            requestPermissions();
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, PERMISSION_ID);
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void turnOnGPS() {
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,5000)
                .setMinUpdateIntervalMillis(3000)
                .build();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Toast.makeText(PremiumTrailActivity.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();

            } catch (ApiException e) {

                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            resolvableApiException.startResolutionForResult(PremiumTrailActivity.this, 2);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Device does not have location
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHECK_SETTING){
            switch (resultCode){
                case Activity.RESULT_OK:
                    Toast.makeText(this, "Location is turned on",Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "Turn on your location",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public void onResume() {
        Log.e("ON RESUME","ON RESUME");
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(dataReceiver, new IntentFilter("data-event"));
        trail_map.onResume();
    }

    @Override
    protected void onStart() {
        Log.e("ON START","ON START");
        super.onStart();
        trail_map.onStart();
    }

    @Override
    protected void onStop() {
        Log.e("ON STOP","ON STOP");
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(dataReceiver);
        trail_map.onStop();
    }

    @Override
    protected void onPause() {
        Log.e("ON PAUSE","ON PAUSE");
        super.onPause();
        trail_map.onPause();
    }

    @Override
    public void onDestroy() {
        Log.e("ON DESTROY","ON DESTROY");
        super.onDestroy();
        // Unregister since the activity is about to be closed.
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(coordsReceiver);
        trail_map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        trail_map.onLowMemory();
    }
}