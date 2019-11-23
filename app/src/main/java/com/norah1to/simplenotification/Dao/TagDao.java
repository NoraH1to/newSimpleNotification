package com.norah1to.simplenotification.Dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.norah1to.simplenotification.Entity.Tag;

import java.util.List;

@Dao
public interface TagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tag tag);

    @Query("SELECT * FROM tag_table WHERE deleted != 1")
    LiveData<List<Tag>> getAllTags();

    @Query("SELECT * FROM tag_table WHERE deleted != 1")
    List<Tag> getAllTagsRaw();

    @Query("UPDATE tag_table SET deleted = :deleteState WHERE tag_id = :tagID")
    int deleteTag(String tagID, int deleteState);

    @Query("SELECT * FROM tag_table WHERE tag_id = :tagID")
    Tag getTag(String tagID);

    @Query("SELECT * FROM tag_table WHERE name = :name")
    Tag getTagByName(String name);

    @Query("UPDATE tag_table SET deleted = 1, modified_timestamp = :time WHERE name = :name")
    int deleteTagByName(String name, long time);

    @Query("SELECT * FROM tag_table WHERE modified_timestamp > :lastSyncTimeStamp " +
            "AND created_timestamp > :lastSyncTimeStamp AND deleted != 1")
    List<Tag> getCreateTags(long lastSyncTimeStamp);

    @Query("SELECT * FROM tag_table WHERE modified_timestamp > :lastSyncTimeStamp " +
            "AND created_timestamp <= :lastSyncTimeStamp AND deleted != 1")
    List<Tag> getModifiedTags(long lastSyncTimeStamp);

    @Query("SELECT * FROM tag_table WHERE modified_timestamp > :lastSyncTimeStamp AND deleted = 1")
    List<Tag> getDeletedTags(long lastSyncTimeStamp);
}
