package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.repositories.PointRepository;

import java.util.List;

public class PointViewModel extends AndroidViewModel {

    private PointRepository pointRepository;

    private LiveData<Point> point;

    public PointViewModel(@NonNull Application application) {
        super(application);
        pointRepository = new PointRepository(application);
    }

    public LiveData<Point> getPoint() {
        return point;
    }

    public void setPointViewModel(int id){
        point = pointRepository.getPointById(id);
    }
}
