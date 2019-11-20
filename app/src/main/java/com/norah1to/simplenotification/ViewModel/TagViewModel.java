package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Repository.TagRepository;

import java.util.List;

public class TagViewModel extends AndroidViewModel {

    private TagRepository mRepository;

    private LiveData<List<Tag>> mAllTags;

    public TagViewModel(Application application) {
        super(application);
        mRepository = new TagRepository(application);
        mAllTags = mRepository.getmAllTags();
    }

    // 全部 tag
    public LiveData<List<Tag>> getmAllTags() {
        return mAllTags;
    }

    // 插入一条
    public void insert(Tag tag) {
        mRepository.insert(tag);
    }

    // 删除一条
    public int delete(Tag tag) {
        return mRepository.deleteTag(tag);
    }

    // 根据内容删掉一条
    public int deleteByName(String name) {
        return mRepository.deleteTagByName(name);
    }

    // 根据内容查找一条
    public Tag getTagByName(String name) {
        return mRepository.getTagByName(name);
    }

    // 根据 tagID 拿到一条
    public Tag getTag(String tagID) {
        return mRepository.getTag(tagID);
    }
}
