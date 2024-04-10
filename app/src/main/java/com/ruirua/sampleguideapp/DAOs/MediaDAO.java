package com.ruirua.sampleguideapp.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.Media;

import java.util.List;

@Dao
public interface MediaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Media> cats);

    @Query("SELECT DISTINCT * FROM media")
    LiveData<List<Media>> getMedias();

    @Query("SELECT * FROM media WHERE media.id = :id")
    LiveData<Media> getMediaById(int id);

    @Query("DELETE FROM media")
    void deleteAll();
}
