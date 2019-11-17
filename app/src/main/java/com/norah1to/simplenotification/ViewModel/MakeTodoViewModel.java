package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.Date;

public class MakeTodoViewModel extends AndroidViewModel {

    private TodoRepository mtodoRepository;

    private MutableLiveData<Todo> mTodo;
    private MutableLiveData<Date> mDate;

    public MakeTodoViewModel(Application application) {
        super(application);
        mtodoRepository = new TodoRepository(application);
        mTodo = new MutableLiveData<Todo>();
        mDate = new MutableLiveData<Date>();
    }

    public void setmTodo(String todoID) {
        Todo tmpTodo = mtodoRepository.getmTodoByID(todoID);
        if (tmpTodo == null)
            return;
        mTodo.postValue(tmpTodo);
        mDate.postValue(tmpTodo.getNoticeTime());
    }

    public MutableLiveData<Todo> getmTodo() {
        return mTodo;
    }

    public MutableLiveData<Date> getmData() {
        return mDate;
    }
}
