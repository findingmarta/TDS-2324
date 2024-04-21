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
import com.ruirua.sampleguideapp.model.TrailWith;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StandardTrailActivity extends AppCompatActivity{
    private PointsRecyclerViewAdapter adapter;
    private int trail_id;
    private TextView trail_name;
    private TextView trail_duration;
    private TextView trail_difficulty;
    private TextView trail_desc;
    private ImageView trail_image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_standard);

        // Set up the Points of Interest RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_standard_points);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // Bind trail's views
        trail_name = findViewById(R.id.standard_trail_name);
        trail_duration = findViewById(R.id.standard_trail_duration);
        trail_difficulty = findViewById(R.id.standard_trail_difficulty);
        trail_desc = findViewById(R.id.standard_trail_desc);
        trail_image = findViewById(R.id.standard_trail_gallery);



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
                setTrailInfo(new_trail);
            }
        });


        // Trail's Points
        LiveData<List<Point>> pointsData = tvm.getTrailPoints(trail_id);
        pointsData.observe(this, pointslist -> {
            ArrayList<Point> points = new ArrayList<>(pointslist);
            adapter = new PointsRecyclerViewAdapter(points,this);
            recyclerView.setAdapter(adapter);
        });

    }


    public void setTrailInfo(TrailWith trailWith){
        Trail trail = trailWith.getTrail();

        trail_name.setText(trail.getTrail_name().toUpperCase());
        trail_duration.setText(String.valueOf(trail.getTrail_duration()));
        trail_difficulty.setText(trail.getTrail_difficulty());
        trail_desc.setText(trail.getTrail_desc());
        Picasso.get()
                .load(trail.getTrail_image().replace("http:", "https:"))
                .into(trail_image);
    }
}