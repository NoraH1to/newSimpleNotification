package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TagRepository;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MakeTodoViewModel extends AndroidViewModel {

    private TodoRepository mtodoRepository;
    private TagRepository mtagRepository;

    private MutableLiveData<Todo> mTodo;
    private MutableLiveData<Date> mDate;
    private MutableLiveData<List<Tag>> mTags;
    private MutableLiveData<Set<Tag>> mAddTags;

    public MakeTodoViewModel(Application application) {
        super(application);
        mtodoRepository = new TodoRepository(application);
        mtagRepository = new TagRepository(application);
        mTags = new MutableLiveData<List<Tag>>();
        mTodo = new MutableLiveData<Todo>();
        mDate = new MutableLiveData<Date>();
        mAddTags = new MutableLiveData<Set<Tag>>();
        mAddTags.postValue(new HashSet<Tag>());
    }

    public void setmTodo(String todoID) {
        Todo tmpTodo = mtodoRepository.getmTodoByID(todoID);
        if (tmpTodo == null)
            return;
        mTodo.postValue(tmpTodo);
        mDate.postValue(tmpTodo.getNoticeTimeStamp());
        mAddTags.postValue(new HashSet<Tag>(tmpTodo.getTags()));
    }

    public void setmTags() {
        mTags.postValue(mtagRepository.getAllTagsRaw());
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

    public MutableLiveData<Set<Tag>> getmAddTags() {
        return mAddTags;
    }
}
