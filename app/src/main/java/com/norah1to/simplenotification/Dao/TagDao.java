package com.norah1to.simplenotification.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Entity.Todo;

import java.util.List;

@Dao
public interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tag tag);

    @Query("SELECT * FROM tag_table")
    LiveData<List<Tag>> getAllTags();

    @Delete
    int deleteTag(Tag tag);
}
