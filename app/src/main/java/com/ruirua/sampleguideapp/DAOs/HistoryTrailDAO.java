package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.ruirua.sampleguideapp.model.Converters.DateTypeConverter;
import com.ruirua.sampleguideapp.model.History_Trail;

import java.util.Date;
import java.util.List;

@Dao
public interface HistoryTrailDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History_Trail trail);


    // UPDATE
    @Query("UPDATE history_trail SET date = :date WHERE id = :id")
    @TypeConverters({DateTypeConverter.class})
    void updateTrailDate(int id, Date date);


    // GET
    @Query("SELECT * FROM history_trail")
    LiveData<List<History_Trail>> getTrails();

    @Query("SELECT * FROM history_trail WHERE history_trail.id = :id")
    LiveData<History_Trail> getHistoryTrailById(int id);

    @Query("SELECT * FROM history_trail WHERE history_trail.trail_id = :trail_id")
    History_Trail getHistoryTrailByTrailId(int trail_id);


    // DELETE
    @Query("DELETE FROM history_trail")
    void deleteAll();

}