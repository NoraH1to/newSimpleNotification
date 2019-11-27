package com.norah1to.simplenotification.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Dao.TodoDao;
import com.norah1to.simplenotification.DataBase.TodoRoomDataBase;
import com.norah1to.simplenotification.Entity.Todo;

import java.util.List;

public class FinishTodoRepository {

    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodos;

    public FinishTodoRepository(Application application) {
        // 获得 TodoRoom 的 db 对象
        TodoRoomDataBase db = TodoRoomDataBase.getDatabase(application);
        // 从 db 中拿到 dao
        mTodoDao = db.todoDao();
        // 拿到数据库中的所有数据
        mAllTodos = mTodoDao.getAllFinishTodos();
    }

    public LiveData<List<Todo>> getmAllTodos() {
        return mAllTodos;
    }

    public int deleteTodo(String todoID, int deleteState) {
        return mTodoDao.deleteTodo(todoID, deleteState);
    }
}
