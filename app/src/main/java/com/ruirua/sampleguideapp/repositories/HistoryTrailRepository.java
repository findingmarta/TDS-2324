package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.DAOs.HistoryTrailDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.History_Trail;

import java.util.Date;
import java.util.List;

public class HistoryTrailRepository {
    private final HistoryTrailDAO historyTrailDAO;

    public HistoryTrailRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        historyTrailDAO = database.historyTrailDAO();
    }

    public void insert(History_Trail trail){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            historyTrailDAO.insert(trail);
        });
    }

    public void updateTrailDate(int trail_id, Date date, int time, int distance){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            historyTrailDAO.updateTrailDate(trail_id,date,time,distance);
        });
    }

    public LiveData<List<History_Trail>> getAllTrails(){
        return historyTrailDAO.getTrails();
    }

    public History_Trail getHistoryTrailByTrailId(int trail_id){
        return historyTrailDAO.getHistoryTrailByTrailId(trail_id);
    }
}
