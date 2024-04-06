package com.ruirua.sampleguideapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.ruirua.sampleguideapp.adapters.PointsRecyclerViewAdapter;
//import com.ruirua.sampleguideapp.adapters.TrailsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.PointOfInterest;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.viewModel.TrailsViewModel;

import java.util.ArrayList;
import java.util.List;
/*
public class PremiumTrailActivity extends AppCompatActivity {
    private PointsRecyclerViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the Points of Interest RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_trails_points);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // Get the info from de database

        // Trail
        TrailsViewModel tvm = new ViewModelProvider(this).get(TrailViewModel.class);

        LiveData<Trail> trailData = tvm.getTrail();
        trailsData.observe(this, new_trail -> {
            Trail trail = new_trail.getTrail();
        });

        // Points
        PointsViewModel pvm = new ViewModelProvider(this).get(PointsViewModel.class);

        LiveData<List<PointOfInterest>> pointsData = pvm.getAllPoints();
        pointsData.observe(this, pointslist -> {
            ArrayList<PointOfInterest> points = new ArrayList<>(pointslist);
            adapter = new PointsRecyclerViewAdapter(points);
            recyclerView.setAdapter(adapter);
        });

    }
}*/