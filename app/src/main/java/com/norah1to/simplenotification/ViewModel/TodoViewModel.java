package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mRepository;

    private LiveData<List<Todo>> mAllTodos;

    public TodoViewModel(Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mAllTodos = mRepository.getmAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    // 插入一条
    public void insert(Todo todo) {
        mRepository.insert(todo);
    }

    // 更新一条
    public void update(Todo todo) {
        // TODO: update res
    }

    // 删除一条
    public void delete(Todo todo) {
        mRepository.deleteTodo(todo);
        // TODO: deleteOne res
    }
}
