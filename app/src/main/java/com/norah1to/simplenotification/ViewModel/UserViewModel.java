package com.norah1to.simplenotification.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.norah1to.simplenotification.Entity.User;
import com.norah1to.simplenotification.Repository.UserRepository;

public class UserViewModel extends AndroidViewModel {

    UserRepository mRepository;

    LiveData<User> mUser;

    public UserViewModel(Application application) {
        super(application);
        mRepository =new UserRepository(application);
        mUser = mRepository.getmUser();
    }

    public LiveData<User> getmUser() {
        return mUser;
    }

    public void insert(User user) {
        mRepository.insert(user);
    }
}
