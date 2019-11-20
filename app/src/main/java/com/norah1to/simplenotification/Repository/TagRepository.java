package com.norah1to.simplenotification.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Dao.TagDao;
import com.norah1to.simplenotification.DataBase.TagRoomDataBase;
import com.norah1to.simplenotification.Entity.Tag;

import java.util.List;

public class TagRepository {
    private TagDao mTagDao;
    private LiveData<List<Tag>> mAllTags;

    public TagRepository(Application application) {
        // 获得 TagRoom 的 db 对象
        TagRoomDataBase db = TagRoomDataBase.getDatabase(application);
        // 拿到 db 中的 dao
        mTagDao = db.tagDao();
        // 拿到数据库中所有的 tag
        mAllTags = mTagDao.getAllTags();
    }

    public LiveData<List<Tag>> getmAllTags() {
        return mAllTags;
    }

    public int deleteTodo(Tag tag) {
        return mTagDao.deleteTag(tag);
    }

    public void insert (Tag tag) {
        new TagRepository.insertAsyncTask(mTagDao).execute(tag);
    }

    // 异步操作数据库
    private static class insertAsyncTask extends AsyncTask<Tag, Void, Void> {

        private TagDao mAsyncTaskDao;

        insertAsyncTask(TagDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Tag... params) {
            // 异步把数据写入数据库
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
