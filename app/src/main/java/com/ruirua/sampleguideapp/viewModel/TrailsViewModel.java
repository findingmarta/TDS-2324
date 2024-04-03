package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.ruirua.sampleguideapp.repositories.TrailRepository;
import com.ruirua.sampleguideapp.model.Trail;
import java.util.List;

public class TrailsViewModel extends AndroidViewModel {

    private TrailRepository trailRepository;

    public LiveData<List<Trail>> trails;

    public TrailsViewModel(@NonNull Application application) {
        super(application);
        trailRepository= new TrailRepository(application);
        trails = trailRepository.getAllTrails();
    }

    public LiveData<List<Trail>> getAllTrails() {
        return trails;
    }
}
