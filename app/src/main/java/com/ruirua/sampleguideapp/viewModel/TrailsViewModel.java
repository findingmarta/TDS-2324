package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.TrailWith;
import com.ruirua.sampleguideapp.repositories.TrailRepository;

import java.util.List;

public class TrailsViewModel extends AndroidViewModel {
    private LiveData<List<TrailWith>> trails;

    public TrailsViewModel(@NonNull Application application) {
        super(application);
        TrailRepository trailRepository = new TrailRepository(application);
        trails = trailRepository.getAllTrails();
    }

    public LiveData<List<TrailWith>> getAllTrails() {
        return trails;
    }
}
