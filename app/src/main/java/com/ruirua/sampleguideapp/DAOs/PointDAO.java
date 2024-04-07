package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.Point;

import java.util.List;

@Dao
public interface PointDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Point> cats);
    //void insertTrail(Trail trail);

    @Query("SELECT DISTINCT * FROM points")
    LiveData<List<Point>> getPoints();

     @Query("DELETE FROM points")
    void deleteAll();
}