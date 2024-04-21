package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;

import java.util.List;

@Dao
public interface HistoryTrailDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History_Trail trail);


    // GET
    @Query("SELECT * FROM history_trail")
    LiveData<List<History_Trail>> getTrails();

    @Query("SELECT * FROM history_trail WHERE history_trail.id = :id")
    LiveData<History_Trail> getHistoryTrailById(int id);


    // DELETE
    @Query("DELETE FROM history_trail")
    void deleteAll();

}