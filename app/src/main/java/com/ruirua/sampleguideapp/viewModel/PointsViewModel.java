package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.repositories.PointRepository;

import java.util.List;

public class PointsViewModel extends AndroidViewModel {

    private PointRepository pointRepository;

    public LiveData<List<Point>> points;

    public PointsViewModel(@NonNull Application application) {
        super(application);
        pointRepository = new PointRepository(application);
        points = pointRepository.getAllPoints();
    }

    public LiveData<List<Point>> getAllPoints() {
        return points;
    }
}
