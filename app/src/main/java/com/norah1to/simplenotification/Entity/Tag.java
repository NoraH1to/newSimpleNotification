package com.norah1to.simplenotification.Entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "tag_table", indices = {
        @Index(value = {"name"}, unique = true)
})
public class Tag implements Serializable {

    private static final String STATE_VISIBLE = "1";
    private static final String STATE_NOT_VISIBLE = "0";

    public static final int STATE_DELETED = 1;
    public static final int STATE_NOT_DELETED = 0;

    public Tag() {
        this.tagID = UUID.randomUUID().toString();
        this.visible = this.STATE_VISIBLE;
        this.createdTimeStamp = new Date();
        this.modifiedTimeStamp = new Date();
        this.deleted = STATE_NOT_DELETED;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        try {
            Tag tag = (Tag)obj;
            if (tag.getName().equals(this.name)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "tag_id")
    private String tagID;

    @ColumnInfo(name = "user_id")
    private String userID;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "deleted")
    private int deleted;

    @ColumnInfo(name = "modified_timestamp")
    private Date modifiedTimeStamp;

    @NonNull
    @ColumnInfo(name = "created_timestamp")
    private Date createdTimeStamp;

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

    public Date getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }

    public void setModifiedTimeStamp(Date modifiedTimeStamp) {
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    @NonNull
    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(@NonNull Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
