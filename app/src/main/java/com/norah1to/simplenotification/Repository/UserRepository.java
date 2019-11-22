package com.norah1to.simplenotification.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Dao.UserDao;
import com.norah1to.simplenotification.DataBase.TodoRoomDataBase;
import com.norah1to.simplenotification.Entity.User;

public class UserRepository {

    private UserDao mUserDao;

    private LiveData<User> mUser;

    public UserRepository(Application application) {
        TodoRoomDataBase db = TodoRoomDataBase.getDatabase(application);
        mUserDao = db.userDao();
        mUser = mUserDao.getUserInfo();
    }

    public LiveData<User> getmUser() {
        return mUser;
    }

    public void insert (User user) {
        new UserRepository.insertAsyncTask(mUserDao).execute(user);
    }

    // 异步操作数据库
    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            // 异步插入
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
