package com.norah1to.simplenotification.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "todo_table")
public class Todo implements Serializable {

    public static final String TAG = "TODO_OBJ";

    public Todo() {
        this.uuid = UUID.randomUUID().toString();
        this.finish = false;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "uuid")
    private String uuid;

    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "date")
    private Date date;

    @NonNull
    @ColumnInfo(name = "create_date")
    private Date createDate;

    @NonNull
    @ColumnInfo(name = "finish")
    private boolean finish;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    @NonNull
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(@NonNull Date createDate) {
        this.createDate = createDate;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
