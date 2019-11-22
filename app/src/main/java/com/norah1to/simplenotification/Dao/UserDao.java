package com.norah1to.simplenotification.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.norah1to.simplenotification.Entity.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user_table")
    LiveData<User> getUserInfo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

}
