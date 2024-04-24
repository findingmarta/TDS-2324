package com.ruirua.sampleguideapp.viewModel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.repositories.PointRepository;

public class PointViewModel extends AndroidViewModel {

    private PointRepository pointRepository;

    private LiveData<PointWith> pointWith;

    public PointViewModel(@NonNull Application application) {
        super(application);
        pointRepository = new PointRepository(application);
    }

    public LiveData<PointWith> getPointWith() {
        return pointWith;
    }

    public void setPointViewModel(int id){
        pointWith = pointRepository.getPointWithById(id);
    }

    public Boolean getPremium () {
        SharedPreferences sp = getApplication().getSharedPreferences("BraGuia Shared Preferences",MODE_PRIVATE);
        return sp.getBoolean("user_type", false);
    }
}
