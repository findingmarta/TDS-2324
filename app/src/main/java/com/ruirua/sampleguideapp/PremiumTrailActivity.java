package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ruirua.sampleguideapp.adapters.PointsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailWith;
import com.ruirua.sampleguideapp.viewModel.HistoryViewModel;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PremiumTrailActivity extends AppCompatActivity implements OnMapReadyCallback {
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

        // Get the info from de database
        // Trail
        TrailViewModel tvm = new ViewModelProvider(this).get(TrailViewModel.class);

        // Get trail's id
        Intent intent = getIntent();
        trail_id = intent.getIntExtra("trail_id",0);

        // Given the ID initialize the trail and set its info
        tvm.setTrailViewModel(trail_id);
        LiveData<TrailWith> trailData = tvm.getTrailWith();
        trailData.observe(this, new_trail -> {
            if (new_trail != null) {
                trail = new_trail.getTrail();
                setTrailInfo();
            }
        });

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
            String link = "https://www.google.com/maps/dir";
            StringBuilder linkMaps = new StringBuilder();
            linkMaps.append(link);
            for(Point p:points){
                linkMaps.append("/").append(p.getPoint_lat()).append(",").append(p.getPoint_lng());
            }
            Log.e("Antes do start","11111111111111");
            creatPathOnMaps(linkMaps.toString());
            trail_map.getMapAsync(this);
        });



        // Set up the Map
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK objects or sub-Bundles.

        //trail_map.getMapAsync(this);

        HistoryViewModel hvm = new ViewModelProvider(this).get(HistoryViewModel.class);
        setStartStop(hvm);

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

    public void setStartStop(HistoryViewModel hvm){
        // Block the Stop button
        stop_button.setClickable(false);

        start_button.setOnClickListener(view -> {
            // Unblock the Stop button and block the Start button
            stop_button.setClickable(true);
            start_button.setClickable(false);

            // Check if the trail is in the history
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                History_Trail historyTrail = hvm.checkHistoryTrail(trail_id);

                if (historyTrail != null){
                    // Update trail if it exists in the history
                    hvm.updateHistoryTrail(trail.getTrailId());
                    // Update UI on the main thread
                    runOnUiThread(() -> {
                        Toast.makeText(PremiumTrailActivity.this, "Trail's history updated!", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    // Insert trail if it doesn't exist in the history
                    hvm.insertHistoryTrail(trail.getTrailId());
                    // Update UI on the main thread
                    runOnUiThread(() -> {
                        Toast.makeText(PremiumTrailActivity.this, "Trail added to your history!", Toast.LENGTH_SHORT).show();
                    });
                }
            });
            // Shutdown executor after use
            executor.shutdown();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        if(points!=null) {
            Log.e("Antes de pôr pontos", points.get(0).toString());
            for (Point p : points) {
                Log.e("Antes de pôr pontos", "2222222222");
                LatLng trail = new LatLng(p.getPoint_lat(), p.getPoint_lng());
                googleMap.addMarker(new MarkerOptions()
                        .position(trail)
                        .title(p.getPoint_name()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trail, 12));
            }
        }
        //LatLng sydney = new LatLng(-33.852, 151.211);
        //googleMap.addMarker(new MarkerOptions()
        //        .position(sydney)
        //        .title("Marker in Sydney"));

        // Move the map's camera to the same location.
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
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



    @Override
    public void onResume() {
        trail_map.onResume();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        trail_map.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        trail_map.onStop();
    }

    @Override
    protected void onPause() {
        trail_map.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        trail_map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        trail_map.onLowMemory();
    }
    public void creatPathOnMaps(String linkMaps){
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(linkMaps);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}