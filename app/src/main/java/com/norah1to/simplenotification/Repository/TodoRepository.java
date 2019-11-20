package com.norah1to.simplenotification.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Dao.TodoDao;
import com.norah1to.simplenotification.DataBase.TodoRoomDataBase;
import com.norah1to.simplenotification.Entity.Todo;

import java.util.List;

public class TodoRepository {

    private TodoDao mTodoDao;
    private LiveData<List<Todo>> mAllTodos;

    public TodoRepository(Application application) {
        // 获得 TodoRoom 的 db 对象
        TodoRoomDataBase db = TodoRoomDataBase.getDatabase(application);
        // 从 db 中拿到 dao
        mTodoDao = db.todoDao();
        // 拿到数据库中的所有数据
        mAllTodos = mTodoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getmAllTodos() {
        return mAllTodos;
    }

    // 根据 todoID 获得数据
    public Todo getmTodoByID(String todoID) {
        return mTodoDao.getTodo(todoID);
    }

    public int deleteTodo(Todo todo) {
        return mTodoDao.deleteTodo(todo);
    }

    public void insert (Todo todo) {
        new insertAsyncTask(mTodoDao).execute(todo);
    }

    // 异步操作数据库
    private static class insertAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mAsyncTaskDao;

        insertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Todo... params) {
            // 异步插入
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
