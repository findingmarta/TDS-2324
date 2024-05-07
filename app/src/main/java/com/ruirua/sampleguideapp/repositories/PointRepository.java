package com.ruirua.sampleguideapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;

import java.util.List;

public class PointRepository {
    private final PointDAO pointDAO;

    public PointRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        pointDAO = database.pointDAO();
    }

    public LiveData<PointWith> getPointWithById(int id){
        return pointDAO.getPointWithById(id);
    }

    public void insert(List<Point> points){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            pointDAO.insert(points);
        });
    }
    public void updateVisited(int point_id){
        pointDAO.updateVisited(point_id);
    }
}
