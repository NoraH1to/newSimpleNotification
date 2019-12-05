package com.norah1to.simplenotification.Settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharePreferencesHelper {

    public static String KEY_USER = "KEY_USER";
    public static String KEY_USER_ACCOUNT = "KEY_USER_ACCOUNT";
    public static String KEY_USER_SESSIONID = "KEY_USER_SESSIONID";


    // 获得用户账号状态，null 没有登入，其它为账号字符串
    public static String getUserState(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(KEY_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ACCOUNT, null);
    }

    // 获得用户 sessionID，如果没有就说明没有登入
    public static String getSessionID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                KEY_USER, Context.MODE_PRIVATE
        );
        return sharedPreferences.getString(KEY_USER_SESSIONID, null);
    }

    // 发送通知的通知设置提醒
    public static Boolean getMakeAlarmWhenNotification(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("makeAlarmWhenNotification", false);
    }
}
