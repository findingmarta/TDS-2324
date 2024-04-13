package com.ruirua.sampleguideapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ruirua.sampleguideapp.DAOs.MediaDAO;
import com.ruirua.sampleguideapp.database.GuideDatabase;
import com.ruirua.sampleguideapp.model.Media;

import java.util.List;

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
