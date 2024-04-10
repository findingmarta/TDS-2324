package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.PointDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
