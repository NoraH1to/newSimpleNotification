package com.norah1to.simplenotification.Repository;

import android.app.Application;

import com.norah1to.simplenotification.Dao.TagDao;
import com.norah1to.simplenotification.Dao.TodoDao;
import com.norah1to.simplenotification.Dao.UserDao;
import com.norah1to.simplenotification.DataBase.TodoRoomDataBase;
import com.norah1to.simplenotification.Entity.Todo;

import java.util.List;

public class SyncRepository {

    private TodoDao mTodoDao;
    private TagDao mTagDao;
    private UserDao mUserDao;

    public SyncRepository(Application application) {
        TodoRoomDataBase db = TodoRoomDataBase.getDatabase(application);
        mTagDao = db.tagDao();
        mTodoDao = db.todoDao();
        mUserDao = db.userDao();
    }

    private List<Todo> getAddTodo() {
        return mTodoDao.getCreateTodos(
                        mUserDao.getUserInfo().getValue().getLastSyncTimestamp().getTime()
                );
    }

    // TODO: 同步数据库操作
}
