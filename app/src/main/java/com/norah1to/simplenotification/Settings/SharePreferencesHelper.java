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
    public static boolean getMakeAlarmWhenNotification(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("pref_makeAlarmWhenNotification", false);
    }

    // 通知的默认提醒小时
    public static int getNoticeDateHour(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(
                sharedPreferences.getString("pref_notice_date_hour", "10"));
    }

    // 通知的默认分钟
    public static int getNoticeDateMin(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(
                sharedPreferences.getString("pref_notice_date_min", "0"));
    }

    // 第二天开始小时
    public static int getSecondDayStartHour(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(
                sharedPreferences.getString("pref_when_second_day_start_hour", "0"));
    }

    // 稍后提醒的时长（分钟）
    public static int getWaitNoticeTimeMin(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(
                sharedPreferences.getString("pref_wait_notice_min", "15"));
    }
}
