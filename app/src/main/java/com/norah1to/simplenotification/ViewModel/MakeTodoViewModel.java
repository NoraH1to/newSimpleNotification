package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.norah1to.simplenotification.Entity.Todo;

import java.util.Date;

public class MakeTodoViewModel extends AndroidViewModel {

    private MutableLiveData<Todo> mTodo;
    private MutableLiveData<Date> mDate;

    public MakeTodoViewModel(Application application) {
        super(application);
        mTodo = new MutableLiveData<Todo>();
        mDate = new MutableLiveData<Date>();
    }

    public MutableLiveData<Todo> getmTodo() {
        if (mTodo == null) {
            mTodo = new MutableLiveData<Todo>();
        }
        return mTodo;
    }

    public MutableLiveData<Date> getmData() {
        if (mDate == null) {
            mDate = new MutableLiveData<Date>();
        }
        return mDate;
    }
}
