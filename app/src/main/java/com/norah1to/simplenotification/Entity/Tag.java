package com.norah1to.simplenotification.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "tag_table", indices = {@Index(value = {"name"}, unique = true)})
public class Tag {

    private static final String STATE_VISIBLE = "1";
    private static final String STATE_NOT_VISIBLE = "0";

    public Tag() {
        // TODO: tag构造方法
        this.tagID = UUID.randomUUID().toString();
        this.visible = this.STATE_VISIBLE;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "tag_id")
    private String tagID;

    @NonNull
    @ColumnInfo(name = "user_id")
    private String userID;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "visible")
    private String visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getVisible() {
        return visible;
    }

    public void setVisible(@NonNull String visible) {
        this.visible = visible;
    }
}
