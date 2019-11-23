package com.norah1to.simplenotification.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "todo_table")
public class Todo implements Serializable {

    public static final String TAG = "TODO_OBJ";

    /**
     * 删除状态
     */
    public static final int STATE_DELETED = 1;
    public static final int STATE_NOT_DELETED = 0;

    /**
     * 提醒状态
     */
    public static final int STATE_NOTICE = 1;
    public static final int STATE_NOT_NOTICE = 0;

    /**
     *  优先级
     */
    public static final int PROIORITY_HIGH = 150;
    public static final int PROIORITY_MID = 100;
    public static final int PROIORITY_LOW = 50;


    public Todo() {
        this.todoID = UUID.randomUUID().toString();
        this.deleted = this.STATE_NOT_DELETED;
        this.tags = new ArrayList<Tag>();
        this.priority = this.PROIORITY_LOW;
        this.notice = this.STATE_NOT_NOTICE;
        this.checked = false;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("todoID: ").append(todoID)
                .append("content: ").append(this.content);

        return stringBuilder.toString();
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @Ignore
    private boolean checked;

    /**
     * 下面的内容是跟云端数据库对应的
     */

    // 待做的专属 ID
    @NonNull
    @ColumnInfo(name = "todo_id")
    private String todoID;

    // 用户的 ID
    @ColumnInfo(name = "user_id")
    private String userID;

    // 待做的内容
    @NonNull
    @ColumnInfo(name = "content")
    private String content;

    // 是否开启提醒
    @ColumnInfo(name = "notice")
    private int notice;

    // 提醒的时间
    @ColumnInfo(name = "notice_timestamp")
    private Date noticeTimeStamp;

    // 创建的时间
    @NonNull
    @ColumnInfo(name = "created_timestamp")
    private Date createdTimeStamp;

    // 完成的时间
    @ColumnInfo(name = "completed_timestamp")
    private Date completedTimeStamp;

    // 最后修改的时间
    @ColumnInfo(name = "modified_timestamp")
    private Date modifiedTimeStamp;

    // 删除状态
    @ColumnInfo(name = "deleted")
    private int deleted;

    // 优先级
    @NonNull
    @ColumnInfo(name = "priority")
    private int priority;

    // 标签
    @NonNull
    @ColumnInfo(name = "tags")
    private List<Tag> tags;

    // 排序用字段
    @ColumnInfo(name = "sort_order")
    private long sortOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @NonNull
    public String getTodoID() {
        return todoID;
    }

    public void setTodoID(@NonNull String todoID) {
        this.todoID = todoID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public Date getNoticeTimeStamp() {
        return noticeTimeStamp;
    }

    public void setNoticeTimeStamp(Date noticeTimeStamp) {
        this.noticeTimeStamp = noticeTimeStamp;
    }

    @NonNull
    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(@NonNull Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Date getCompletedTimeStamp() {
        return completedTimeStamp;
    }

    public void setCompletedTimeStamp(Date completedTimeStamp) {
        this.completedTimeStamp = completedTimeStamp;
    }

    public Date getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }

    public void setModifiedTimeStamp(Date modifiedTimeStamp) {
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @NonNull
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(@NonNull List<Tag> tags) {
        this.tags = tags;
    }

    public long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(long sortOrder) {
        this.sortOrder = sortOrder;
    }
}
