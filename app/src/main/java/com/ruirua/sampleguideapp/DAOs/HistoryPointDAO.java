package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;

import java.util.List;

@Dao
public interface HistoryPointDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History_Point point);


    // GET
    @Query("SELECT * FROM history_point")
    LiveData<List<History_Point>> getPoints();

    @Query("SELECT * FROM history_point WHERE history_point.id = :id")
    LiveData<History_Point> getHistoryPointById(int id);


    // DELETE
    @Query("DELETE FROM history_point")
    void deleteAll();

}