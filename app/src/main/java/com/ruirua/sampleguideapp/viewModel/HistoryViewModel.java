package com.ruirua.sampleguideapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.repositories.HistoryPointRepository;
import com.ruirua.sampleguideapp.repositories.HistoryTrailRepository;

import java.util.Date;
import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private HistoryTrailRepository trailRepository;
    private HistoryPointRepository pointRepository;
    private LiveData<List<History_Trail>> history_trails;
    private LiveData<List<History_Point>> history_points;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        trailRepository= new HistoryTrailRepository(application);
        pointRepository = new HistoryPointRepository(application);

        history_trails = trailRepository.getAllTrails();
        history_points = pointRepository.getAllPoints();
    }

    public LiveData<List<History_Trail>> getAllTrails() {
        return history_trails;
    }

    public LiveData<List<History_Point>> getAllPoints() {
        return history_points;
    }

    public void insertHistoryPoint(int point_id){
        Date date = new Date(System.currentTimeMillis());
        History_Point history_point = new History_Point(point_id,date);
        pointRepository.insert(history_point);
    }

    public LiveData<History_Point> checkHistoryPoint(int point_id){
        return pointRepository.getHistoryPointById(point_id);
    }
}
