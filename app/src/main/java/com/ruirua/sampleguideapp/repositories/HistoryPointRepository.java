package com.ruirua.sampleguideapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.DAOs.HistoryPointDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.History_Point;

import java.util.List;

public class HistoryPointRepository {

    public HistoryPointDAO historyPointDAO;

    public HistoryPointRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        historyPointDAO = database.historyPointDAO();
    }

    public void insert(History_Point point){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            historyPointDAO.insert(point);
        });
    }

    public LiveData<List<History_Point>> getAllPoints(){
        return historyPointDAO.getPoints();
    }

    public LiveData<History_Point> getHistoryPointByPointId(int point_id){
        return historyPointDAO.getHistoryPointByPointId(point_id);
    }
    /*public History_Point getHistoryPointByPointId(int point_id) {
        return historyPointDAO.getHistoryPointByPointId(point_id);
    }*/

    public LiveData<History_Point> getHistoryPointById(int id){
        return historyPointDAO.getHistoryPointById(id);
    }

}
