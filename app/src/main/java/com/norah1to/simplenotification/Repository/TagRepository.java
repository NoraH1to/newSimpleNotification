package com.norah1to.simplenotification.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Dao.TagDao;
import com.norah1to.simplenotification.DataBase.TodoRoomDataBase;
import com.norah1to.simplenotification.Entity.Tag;

import java.util.Date;
import java.util.List;

public class TagRepository {
    private TagDao mTagDao;
    private LiveData<List<Tag>> mAllTags;

    public TagRepository(Application application) {
        // 获得 TodoRoom 的 db 对象
        TodoRoomDataBase db = TodoRoomDataBase.getDatabase(application);
        // 拿到 db 中的 dao
        mTagDao = db.tagDao();
        // 拿到数据库中所有的 tag
        mAllTags = mTagDao.getAllTags();
    }

    public LiveData<List<Tag>> getmAllTags() {
        return mAllTags;
    }

    public List<Tag> getAllTagsRaw() {
        return mTagDao.getAllTagsRaw();
    }

    public int deleteTag(Tag tag) {
        return mTagDao.deleteTag(tag.getTagID(), Tag.STATE_DELETED);
    }

    public int deleteTagByName(String name) {
        return mTagDao.deleteTagByName(name, new Date().getTime());
    }

    public int deleteAll() {
        return mTagDao.deleteAll();
    }

    public void insert (Tag... tags) {
        new TagRepository.insertAsyncTask(mTagDao).execute(tags);
    }

    public Tag getTag (String tagID) {
        return mTagDao.getTag(tagID);
    }

    public Tag getTagByName (String name) {
        return mTagDao.getTagByName(name);
    }

    public List<Tag> getCreated (long lastSyncTimestamp) {
        return mTagDao.getCreateTags(lastSyncTimestamp);
    }

    public List<Tag> getModified (long lastSyncTimestamp) {
        return mTagDao.getModifiedTags(lastSyncTimestamp);
    }

    public List<Tag> getDeleted (long lastSyncTimestamp) {
        return mTagDao.getDeletedTags(lastSyncTimestamp);
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
            mAsyncTaskDao.insert(params);
            return null;
        }
    }
}
