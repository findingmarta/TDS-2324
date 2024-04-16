package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)          // must be an entity
    void insertHistory_Point(History_Point historyPoint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHistory_Trail(History_Trail historyTrail);*/

    // GET
    @Query("SELECT DISTINCT * FROM user LIMIT 1")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user WHERE user.userId = :id")
    LiveData<User> getUserById(int id);

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<History_Point>> getUser_HistoryPoints();

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<History_Trail>> getUser_HistoryTrails();


    // DELETE
    @Query("DELETE FROM user")
    void deleteAll();
}
