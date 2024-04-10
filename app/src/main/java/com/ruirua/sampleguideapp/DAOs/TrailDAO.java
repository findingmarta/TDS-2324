package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;

import java.util.List;

@Dao
public interface TrailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Trail> cats);
    //void insertTrail(Trail trail);

    @Query("SELECT DISTINCT * FROM trail")
    LiveData<List<Trail>> getTrails();

    @Query("SELECT * FROM trail WHERE trail.id = :id")
    LiveData<Trail> getTrailById(int id);

    //@Query("SELECT DISTINCT * FROM trail WHERE trail.id = :id")
    @Query("SELECT DISTINCT * FROM point WHERE point.id = :id")    // TODO Corrigir!!!!
    LiveData<List<Point>> getTrailPoints(int id);

    @Query("DELETE FROM trail")
    void deleteAll();
}