package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> cats);
    //void insertTrail(Trail trail);

    @Query("SELECT DISTINCT * FROM users")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM users WHERE users.id = :id")
    LiveData<User> getUserById(int id);

    @Query("DELETE FROM users")
    void deleteAll();
}
