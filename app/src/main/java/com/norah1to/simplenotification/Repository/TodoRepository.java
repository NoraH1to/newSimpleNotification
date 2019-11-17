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
        TodoRoomDataBase db = TodoRoomDataBase.getDatabase(application);
        mTodoDao = db.todoDao();
        mAllTodos = mTodoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getmAllTodos() {
        return mAllTodos;
    }

    public Todo getmTodoByID(String todoID) {
        return mTodoDao.getTodo(todoID);
    }

    public int deleteTodo(Todo todo) {
        return mTodoDao.deleteTodo(todo);
    }

    public void insert (Todo todo) {
        new insertAsyncTask(mTodoDao).execute(todo);
    }

    private static class insertAsyncTask extends AsyncTask<Todo, Void, Void> {

        private TodoDao mAsyncTaskDao;

        insertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Todo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
