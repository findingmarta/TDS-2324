package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import com.ruirua.sampleguideapp.model.Converters.DateTypeConverter;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;

import java.util.Date;
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

    @Query("UPDATE point SET visited = 1 WHERE pointId = :id")
    void updateVisited(int id);

}