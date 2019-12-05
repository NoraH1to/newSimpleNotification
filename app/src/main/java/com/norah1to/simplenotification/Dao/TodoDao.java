package com.norah1to.simplenotification.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.norah1to.simplenotification.Entity.Todo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Todo... todo);

    @Query("DELETE FROM todo_table")
    int deleteAll();

    @Query("UPDATE todo_table SET deleted = :deleteState, modified_timestamp = strftime('%s','now')*1000 WHERE todo_id = :todoID")
    int deleteTodo(String todoID, int deleteState);

    @Delete
    int deleteTodos(List<Todo> todos);

    @Query("SELECT * FROM todo_table WHERE deleted != 1 AND completed_timestamp IS NULL ORDER BY sort_order DESC, created_timestamp DESC")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM todo_table WHERE deleted != 1 AND completed_timestamp IS NOT NULL ORDER BY sort_order DESC, created_timestamp DESC")
    LiveData<List<Todo>> getAllFinishTodos();

    @Query("SELECT * FROM todo_table WHERE todo_id=:todoID")
    Todo getTodo(String todoID);

    @Query("SELECT * FROM todo_table WHERE notice != 0 AND deleted != 1")
    List<Todo> getNoticeTodos();

    @Update
    void updateTodo(Todo... todos);

    @Query("SELECT * FROM todo_table WHERE modified_timestamp > :lastSyncTimeStamp " +
            "AND created_timestamp > :lastSyncTimeStamp AND deleted != 1")
    List<Todo> getCreateTodos(long lastSyncTimeStamp);

    @Query("SELECT * FROM todo_table WHERE modified_timestamp > :lastSyncTimeStamp " +
            "AND created_timestamp <= :lastSyncTimeStamp AND deleted != 1")
    List<Todo> getModifiedTodos(long lastSyncTimeStamp);

    @Query("SELECT * FROM todo_table WHERE modified_timestamp > :lastSyncTimeStamp AND deleted = 1")
    List<Todo> getDeletedTodos(long lastSyncTimeStamp);
}
