package com.ruirua.sampleguideapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.Media;

import java.util.List;

public interface MediaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Media> cats);
    //void insertTrail(Trail trail);

    @Query("SELECT DISTINCT * FROM medias")
    LiveData<List<Media>> getMedias();

    @Query("SELECT * FROM medias WHERE medias.id = :id")
    LiveData<Media> getMediaById(int id);

    @Query("DELETE FROM medias")
    void deleteAll();
}
