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
    private HistoryTrailRepository historyTrailRepository;
    private HistoryPointRepository historyPointRepository;
    private LiveData<List<History_Trail>> history_trails;
    private LiveData<List<History_Point>> history_points;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyTrailRepository= new HistoryTrailRepository(application);
        historyPointRepository = new HistoryPointRepository(application);

        history_trails = historyTrailRepository.getAllTrails();
        history_points = historyPointRepository.getAllPoints();
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
        historyPointRepository.insert(history_point);
    }

    public LiveData<History_Point> checkHistoryPoint(int point_id){
        return historyPointRepository.getHistoryPointByPointId(point_id);
    }
    /*public History_Point checkHistoryPoint(int point_id){
        return historyPointRepository.getHistoryPointByPointId(point_id);
    }*/

    public void insertHistoryTrail(int trail_id){
        Date date = new Date(System.currentTimeMillis());

        History_Trail history_trail = new History_Trail(trail_id,date,0,0);
        historyTrailRepository.insert(history_trail);
    }

    public void updateHistoryTrail(int trail_id){
        Date date = new Date(System.currentTimeMillis());
        historyTrailRepository.updateTrailDate(trail_id,date);
    }

    public History_Trail checkHistoryTrail(int trail_id){
        return historyTrailRepository.getHistoryTrailByTrailId(trail_id);
    }
}
