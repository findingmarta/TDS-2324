package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;

import java.util.List;

@Dao
public interface PointDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Point> cats);


    // GET
    @Query("SELECT * FROM point")
    LiveData<List<Point>> getPoints();

    @Query("SELECT * FROM point WHERE point.pointId = :id")
    LiveData<Point> getPointById(int id);

    @Transaction
    @Query("SELECT * FROM point WHERE point.pointId = :id")
    LiveData<PointWith> getPointWithById(int id);


    // DELETE
    @Query("DELETE FROM point")
    void deleteAll();

}