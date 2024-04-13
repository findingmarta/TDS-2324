package com.ruirua.sampleguideapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Point;

import java.util.List;

public class PointRepository {

    public PointDAO pointDAO;

    public PointRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        pointDAO = database.pointDAO();
    }

    public LiveData<List<Point>> getAllPoints(){
        return pointDAO.getPoints();
    }

    public LiveData<Point> getPointById(int id){
        return pointDAO.getPointById(id);
    }

    public void insert(List<Point> points){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            pointDAO.insert(points);
        });
    }
}
