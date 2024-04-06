package com.ruirua.sampleguideapp;


import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.adapters.TrailsRecyclerViewAdapter;
import com.ruirua.sampleguideapp.viewModel.TrailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class TrailsActivity extends GeneralActivity {
    private TrailsRecyclerViewAdapter adapter;
    @Override
    protected int getContentViewId() {
        return R.layout.activity_trails;
    }
    protected int getNavBarItemSelected () {
        return R.id.Trails;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rv_trails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        // Get the info from de database
        TrailsViewModel tvm = new ViewModelProvider(this).get(TrailsViewModel.class);

        LiveData<List<Trail>> trailsData = tvm.getAllTrails();
        trailsData.observe(this, trailslist -> {
            ArrayList<Trail> trails = new ArrayList<>(trailslist);
            adapter = new TrailsRecyclerViewAdapter(trails);
            recyclerView.setAdapter(adapter);
        });

    }
}