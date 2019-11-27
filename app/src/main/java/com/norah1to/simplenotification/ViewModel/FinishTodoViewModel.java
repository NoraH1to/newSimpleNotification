package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.FinishTodoRepository;

import java.util.List;

public class FinishTodoViewModel extends AndroidViewModel {

    private FinishTodoRepository mRepository;

    private LiveData<List<Todo>> mAllFinishTodos;

    public FinishTodoViewModel(Application application) {
        super(application);
        mRepository = new FinishTodoRepository(application);
        mAllFinishTodos = mRepository.getmAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return mAllFinishTodos;
    }

    // 删除一条
    public void delete(String todoID, int deleteState) {
        mRepository.deleteTodo(todoID, deleteState);
    }
}
