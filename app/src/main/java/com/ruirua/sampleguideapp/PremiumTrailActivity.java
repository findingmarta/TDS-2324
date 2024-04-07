package com.ruirua.sampleguideapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.adapters.PointsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.viewModel.PointsViewModel;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PremiumTrailActivity extends AppCompatActivity {
    private PointsRecyclerViewAdapter adapter;
    private int trail_id;
    private TextView trail_name;
    private TextView trail_duration;
    private TextView trail_difficulty;
    private TextView trail_desc;
    private ImageView trail_image;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_premium);

        // Set up the Points of Interest RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_premium_points);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

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
        trail_id = intent.getIntExtra("id_trail",0);

        // Given the ID initialize the trail
        tvm.setTrailViewModel(trail_id);

        LiveData<Trail> trailData = tvm.getTrail();
        trailData.observe(this, new_trail -> {
            if (new_trail != null) {
                setInfo(new_trail.getTrail());
            }
        });

        // Points
        PointsViewModel pvm = new ViewModelProvider(this).get(PointsViewModel.class);

        LiveData<List<Point>> pointsData = pvm.getAllPoints();
        pointsData.observe(this, pointslist -> {
            ArrayList<Point> points = new ArrayList<>(pointslist);
            adapter = new PointsRecyclerViewAdapter(points);
            recyclerView.setAdapter(adapter);
        });

    }

    public void setInfo(Trail trail){
        trail_name.setText(trail.getTrailName());
        trail_duration.setText(trail.getTrailDuration());
        trail_difficulty.setText(trail.getTrailDifficulty());
        trail_desc.setText(trail.getTrailDesc());
        Picasso.get()
                .load(trail.getTrailImage().replace("http:", "https:"))
                .into(trail_image);
    }

}