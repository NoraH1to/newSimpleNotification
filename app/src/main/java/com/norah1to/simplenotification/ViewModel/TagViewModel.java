package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Repository.TagRepository;

import java.util.List;

public class TagViewModel extends AndroidViewModel {

    private TagRepository mRepository;

    private LiveData<List<Tag>> mTags;

    public TagViewModel(Application application) {
        super(application);
        mRepository = new TagRepository(application);
        mTags = mRepository.getmAllTags();
    }

    // 全部 tag
    public LiveData<List<Tag>> getmTags() {
        return mTags;
    }

    // 插入一条
    public void insert(Tag tag) {
        mRepository.insert(tag);
    }

    // 删除一条
    public int delete(Tag tag) {
        return mRepository.deleteTag(tag);
    }
}
