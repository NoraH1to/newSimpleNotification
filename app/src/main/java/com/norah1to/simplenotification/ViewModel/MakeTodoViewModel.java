package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TagRepository;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.Date;
import java.util.List;

public class MakeTodoViewModel extends AndroidViewModel {

    private TodoRepository mtodoRepository;
    private TagRepository mtagRepository;

    private MutableLiveData<Todo> mTodo;
    private MutableLiveData<Date> mDate;
    private MutableLiveData<List<Tag>> mTags;

    public MakeTodoViewModel(Application application) {
        super(application);
        mtodoRepository = new TodoRepository(application);
        mtagRepository = new TagRepository(application);
        mTags = new MutableLiveData<List<Tag>>();
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

    public void setmTags() {
        mTags.postValue(mtagRepository.getmAllTags().getValue());
    }

    public MutableLiveData<Todo> getmTodo() {
        return mTodo;
    }

    public MutableLiveData<Date> getmData() {
        return mDate;
    }

    public MutableLiveData<List<Tag>> getmTags() {
        return mTags;
    }
}
