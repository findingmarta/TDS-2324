package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.repositories.AppRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private String cookies = "";

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void postLogin (String username, String password) {

    }

    }
