package com.ruirua.sampleguideapp.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ruirua.sampleguideapp.BuildConfig;
import com.ruirua.sampleguideapp.DAOs.MediaDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Media;
import com.ruirua.sampleguideapp.model.MediaAPI;
import com.ruirua.sampleguideapp.model.Point;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediaRepository {

    public MediaDAO mediaDAO;

    public MediaRepository(Application application){
        GuideDatabase database = GuideDatabase.getInstance(application);
        mediaDAO = database.mediaDAO();
    }

    public LiveData<List<Media>> getAllMedias(){
        return mediaDAO.getMedias();
    }

    public LiveData<Media> getMediaById(int id){
        return mediaDAO.getMediaById(id);
    }

    public void insert(List<Media> medias){
        GuideDatabase.databaseWriteExecutor.execute(() -> {
            mediaDAO.insert(medias);
        });
    }
}
