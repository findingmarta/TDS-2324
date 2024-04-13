package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.User;
import com.ruirua.sampleguideapp.repositories.AppRepository;
import com.ruirua.sampleguideapp.repositories.TrailRepository;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    private AppRepository appRepository;
    private LiveData<List<App>> apps;

    public AppViewModel(@NonNull Application application) {
        super(application);
        appRepository= new AppRepository(application);
        apps = appRepository.getAllApps();
    }

    public LiveData<List<App>> getApps() {
        return apps;
    }
}
