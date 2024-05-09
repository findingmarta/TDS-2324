package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ruirua.sampleguideapp.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);


    // GET
    @Query("SELECT DISTINCT * FROM user LIMIT 1")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user WHERE user.userId = :id")
    LiveData<User> getUserById(int id);


    // DELETE
    @Query("DELETE FROM user")
    void deleteAll();
}
