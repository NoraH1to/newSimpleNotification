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
    void deleteAll();

    @Delete
    void deleteTodo(Todo todo);

    @Delete
    void deleteTodos(Todo[] todos);
    // TODO: maybe not work

    @Query("SELECT * FROM todo_table ORDER BY create_date DESC")
    LiveData<List<Todo>> getAllTodos();

    @Update
    void updateTodo(Todo... todos);
}
