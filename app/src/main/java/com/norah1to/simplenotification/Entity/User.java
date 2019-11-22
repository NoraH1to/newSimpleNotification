package com.norah1to.simplenotification.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "user_table")
public class User {

    @ColumnInfo(name = "account")
    @NonNull
    private String account;

    @ColumnInfo(name = "password")
    @NonNull
    private String password;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userID")
    private String userID;

    @ColumnInfo(name = "last_sync_timestamp")
    private Date lastSyncTimestamp;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getLastSyncTimestamp() {
        return lastSyncTimestamp;
    }

    public void setLastSyncTimestamp(Date lastSyncTimestamp) {
        this.lastSyncTimestamp = lastSyncTimestamp;
    }
}
