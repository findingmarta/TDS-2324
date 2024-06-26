package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.repositories.AppRepository;

public class AppViewModel extends AndroidViewModel {
    private LiveData<AppWith> app;

    public AppViewModel(@NonNull Application application) {
        super(application);
        AppRepository appRepository = new AppRepository(application);
        app = appRepository.getAppWith();
    }

    public LiveData<AppWith> getApp() {
        return app;
    }
}
