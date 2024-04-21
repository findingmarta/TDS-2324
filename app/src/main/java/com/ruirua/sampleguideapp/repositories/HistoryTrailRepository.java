package com.ruirua.sampleguideapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.DAOs.HistoryPointDAO;
import com.ruirua.sampleguideapp.DAOs.HistoryTrailDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;

import java.util.List;

public class HistoryTrailRepository {
    public HistoryTrailDAO historyTrailDAO;

    public HistoryTrailRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        historyTrailDAO = database.historyTrailDAO();
    }

    public void insert(History_Trail trail){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            historyTrailDAO.insert(trail);
        });
    }


    public LiveData<List<History_Trail>> getAllTrails(){
        return historyTrailDAO.getTrails();
    }

    public LiveData<History_Trail> getHistoryPointById(int id){
        return historyTrailDAO.getHistoryTrailById(id);
    }

}
