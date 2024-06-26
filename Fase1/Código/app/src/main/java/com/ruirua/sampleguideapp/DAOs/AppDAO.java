package com.ruirua.sampleguideapp.DAOs;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.AppWith;
import com.ruirua.sampleguideapp.model.Contact;
import com.ruirua.sampleguideapp.model.Partner;
import com.ruirua.sampleguideapp.model.Social;

import java.util.List;

@Dao
public interface AppDAO {
    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApp(App app);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSocials(List<Social> socials);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPartners(List<Partner> partners);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertContacts(List<Contact> contacts);


    // GET
    @Transaction
    @Query("SELECT * FROM app")
    LiveData<AppWith> getAppWith();


    //DELETE
    @Delete
    void delete(App app);

    @Transaction
    @Query("DELETE FROM app")
    void deleteAll();
}