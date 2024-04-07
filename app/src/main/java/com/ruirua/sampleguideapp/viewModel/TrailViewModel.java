package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.repositories.TrailRepository;

import java.util.List;

public class TrailViewModel extends AndroidViewModel {

    private TrailRepository trailRepository;

    public LiveData<Trail> trail;

    public TrailViewModel(@NonNull Application application) {
        super(application);
        trailRepository= new TrailRepository(application);
    }

    public LiveData<Trail> getTrail() {
        return trail;
    }

    public void setTrailViewModel(int id){
        trail = trailRepository.getTrailById(id);
    }
}
