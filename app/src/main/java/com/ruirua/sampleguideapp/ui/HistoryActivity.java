package com.ruirua.sampleguideapp.ui;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.adapters.HistoryPointsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.adapters.HistoryTrailsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.viewModel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private HistoryPointsRecyclerViewAdapter adapterPoints;
    private HistoryTrailsRecyclerViewAdapter adapterTrails;
    private Button history_points_button;
    private Button history_trails_button;
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.rv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // Bind buttons
        history_trails_button = findViewById(R.id.history_trails_button);
        history_points_button = findViewById(R.id.history_points_button);

        // Get the info from de database
        HistoryViewModel hvm = new ViewModelProvider(this).get(HistoryViewModel.class);

        // Trails
        ArrayList<History_Trail> trails = new ArrayList<>();
        // Initialize the adapter
        adapterTrails = new HistoryTrailsRecyclerViewAdapter(trails,this);
        recyclerView.setAdapter(adapterTrails);
        LiveData<List<History_Trail>> trailsData = hvm.getAllTrails();
        trailsData.observe(this, trailList -> {
            // Update the adapter
            ArrayList<History_Trail> new_trails = new ArrayList<>(trailList);
            adapterTrails.setTrails(new_trails);
            recyclerView.setAdapter(adapterTrails);
        });

        // Points
        ArrayList<History_Point> points = new ArrayList<>();
        // Initialize the adapter
        adapterPoints = new HistoryPointsRecyclerViewAdapter(points,this);

        LiveData<List<History_Point>> pointsData = hvm.getAllPoints();
        pointsData.observe(this, pointList -> {
            // Update the adapter
            ArrayList<History_Point> new_points = new ArrayList<>(pointList);
            adapterPoints.setPoints(new_points);
        });

        setTrailsButton();
        setPointsButton();
    }


    public void setTrailsButton () {
        history_trails_button.setOnClickListener(view -> {
            // Change button's color
            history_trails_button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HistoryActivity.this, R.color.light_yellow)));
            history_points_button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HistoryActivity.this, R.color.logo_yellow)));

            // Select the trail's adapter
            recyclerView.setAdapter(adapterTrails);
        });
    }

    public void setPointsButton () {
        history_points_button.setOnClickListener(view -> {
            // Change button's color
            history_points_button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HistoryActivity.this, R.color.light_yellow)));
            history_trails_button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(HistoryActivity.this, R.color.logo_yellow)));

            // Select the point's adapter
            recyclerView.setAdapter(adapterPoints);
        });
    }
}