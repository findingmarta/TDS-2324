package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.Trail;

import java.util.List;

@Dao
public interface TrailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Trail> cats);
    //void insertTrail(Trail trail);

    @Query("SELECT DISTINCT * FROM trails")
    LiveData<List<Trail>> getTrails();

    @Query("DELETE FROM trails")
    void deleteAll();
}