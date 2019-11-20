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
    void insert(Todo todo);

    @Query("DELETE FROM todo_table")
    int deleteAll();

    @Delete
    int deleteTodo(Todo todo);

    @Delete
    int deleteTodos(List<Todo> todos);

    @Query("SELECT * FROM todo_table ORDER BY sort_order DESC, created_timestamp DESC")
    LiveData<List<Todo>> getAllTodos();

    @Query("SELECT * FROM todo_table WHERE todo_id=:todoID")
    Todo getTodo(String todoID);

    @Update
    void updateTodo(Todo... todos);
}
