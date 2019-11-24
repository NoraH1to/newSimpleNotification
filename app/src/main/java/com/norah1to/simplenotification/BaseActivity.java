package com.norah1to.simplenotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.norah1to.simplenotification.Entity.User;
import com.norah1to.simplenotification.Settings.SharePreferencesHelper;
import com.norah1to.simplenotification.ViewModel.UserViewModel;

public class BaseActivity extends AppCompatActivity {

    public static UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getmUser().observe(this, mUser -> {
            upDateUserInSharedPreferences(mUser);
        });
    }


    // 更新键值对中的用户信息
    private void upDateUserInSharedPreferences(User user) {
        String account = null;
        if (user != null) {
            account = user.getAccount();
        }
        SharedPreferences sharedPreferences = this
                .getSharedPreferences(SharePreferencesHelper.KEY_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit
                .putString(SharePreferencesHelper.KEY_USER_ACCOUNT, account)
                .apply();
    }
}
