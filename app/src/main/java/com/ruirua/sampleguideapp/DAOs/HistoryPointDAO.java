package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.History_Point;

import java.util.List;

@Dao
public interface HistoryPointDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History_Point point);


    // GET
    @Query("SELECT * FROM history_point")
    LiveData<List<History_Point>> getPoints();

    @Query("SELECT * FROM history_point WHERE history_point.point_id = :point_id")
    LiveData<History_Point> getHistoryPointByPointId(int point_id);

    @Query("SELECT * FROM history_point WHERE history_point.id = :id")
    LiveData<History_Point> getHistoryPointById(int id);


    // DELETE
    @Query("DELETE FROM history_point")
    void deleteAll();

}