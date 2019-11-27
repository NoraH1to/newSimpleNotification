package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TagRepository;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository mTodoRepository;

    private TagRepository mTagRepository;

    private LiveData<List<Todo>> mAllTodos;

    public TodoViewModel(Application application) {
        super(application);
        mTodoRepository = new TodoRepository(application);
        mTagRepository = new TagRepository(application);
        mAllTodos = mTodoRepository.getmAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public void insertTodo(Todo... todo) {
        mTodoRepository.insert(todo);
    }

    public void insertTag(Tag... tags) {
        mTagRepository.insert(tags);
    }

    public int deleteAllTag() {
        return mTagRepository.deleteAll();
    }

    public int deleteAllTodo() {
        return mTodoRepository.deleteAll();
    }

    public List<Tag> getCreatedTags (long lastSyncTimestamp) {
        return mTagRepository.getCreated(lastSyncTimestamp);
    }

    public List<Tag> getModifiedTags (long lastSyncTimestamp) {
        return mTagRepository.getModified(lastSyncTimestamp);
    }

    public List<Tag> getDeletedTags (long lastSyncTimestamp) {
        return mTagRepository.getDeleted(lastSyncTimestamp);
    }

    public List<Todo> getCreatedTodos (long lastSyncTimestamp) {
        return mTodoRepository.getCreated(lastSyncTimestamp);
    }

    public List<Todo> getModifiedTodos (long lastSyncTimestamp) {
        return mTodoRepository.getModified(lastSyncTimestamp);
    }

    public List<Todo> getDeletedTodos (long lastSyncTimestamp) {
        return mTodoRepository.getDeleted(lastSyncTimestamp);
    }

    // 更新一条
    public void update(Todo todo) {
        mTodoRepository.update(todo);
        // TODO: update res
    }

    // 删除一条
    public void delete(String todoID, int deleteState) {
        mTodoRepository.deleteTodo(todoID, deleteState);
    }
}
