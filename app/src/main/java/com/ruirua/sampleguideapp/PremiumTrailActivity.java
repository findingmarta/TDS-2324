package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruirua.sampleguideapp.adapters.PointsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.viewModel.PointsViewModel;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PremiumTrailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private PointsRecyclerViewAdapter adapter;
    private int trail_id;
    private TextView trail_name;
    private TextView trail_duration;
    private TextView trail_difficulty;
    private TextView trail_desc;
    private ImageView trail_image;

    private MapView trail_map;
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



        // Get the info from de database
        // Trail
        TrailViewModel tvm = new ViewModelProvider(this).get(TrailViewModel.class);

        // Get trail's id
        Intent intent = getIntent();
        trail_id = intent.getIntExtra("trail_id",0);

        // Given the ID initialize the trail and set its info
        tvm.setTrailViewModel(trail_id);
        LiveData<Trail> trailData = tvm.getTrail();
        trailData.observe(this, new_trail -> {
            if (new_trail != null) {
                setTrailInfo(new_trail);
            }
        });


        // Trail's Points
        LiveData<List<Point>> pointsData = tvm.getTrailPoints(trail_id);
        pointsData.observe(this, pointslist -> {
            ArrayList<Point> points = new ArrayList<>(pointslist);
            adapter = new PointsRecyclerViewAdapter(points);
            recyclerView.setAdapter(adapter);
        });



        // Set up the Map
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        trail_map = findViewById(R.id.premium_mapView);
        trail_map.onCreate(mapViewBundle);
        trail_map.getMapAsync(this);
    }


    public void setTrailInfo(Trail trail){
        trail_name.setText(trail.getTrail_name().toUpperCase());
        trail_duration.setText(String.valueOf(trail.getTrail_duration()));
        trail_difficulty.setText(trail.getTrail_difficulty());
        trail_desc.setText(trail.getTrail_desc());
        Picasso.get()
                .load(trail.getTrail_image().replace("http:", "https:"))
                .into(trail_image);
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        // Move the map's camera to the same location.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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

}