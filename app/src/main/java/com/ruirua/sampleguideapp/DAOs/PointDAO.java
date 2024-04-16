package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.model.Trail;

import java.util.List;

@Dao
public interface PointDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Point> cats);

    @Query("SELECT DISTINCT * FROM point")
    LiveData<List<Point>> getPoints();

    @Query("SELECT * FROM point WHERE point.pointId = :id")
    LiveData<Point> getPointById(int id);

     @Query("DELETE FROM point")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM point")
    LiveData<List<PointWith>> getPointWith();


}