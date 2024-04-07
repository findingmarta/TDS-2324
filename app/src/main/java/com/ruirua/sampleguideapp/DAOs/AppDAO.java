package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.Point;

import java.util.List;

@Dao
public interface AppDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<App> cats);
    //void insertTrail(Trail trail);

    @Query("SELECT DISTINCT * FROM apps")
    LiveData<List<App>> getApps();

     @Query("DELETE FROM apps")
    void deleteAll();
}