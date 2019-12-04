package com.norah1to.simplenotification.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.norah1to.simplenotification.BroadcastReceiver.ActionNoticeReceiver;
import com.norah1to.simplenotification.Entity.Todo;

public class ActionMakeAlarm extends ActionCreate {

    public static final String TAG = "ActionMakeAlarm";

    Action action;

    public ActionMakeAlarm(Action action) {
        this.action = action;
    }

    @Override
    public void doAction(Context context, Todo todo, Intent intent) {
        action.doAction(context, todo, intent);

        // 小于当前时间的提醒不作处理
//        if (todo.getNoticeTimeStamp().getTime() <= System.currentTimeMillis()) {
//            return;
//        }
        // 克隆 intent
        Intent alarmIntent = (Intent) intent.clone();
        // 通过 Bundle 来传值
        Bundle bundle = new Bundle();
        bundle.putSerializable(Todo.TAG, todo);
        alarmIntent.putExtra("bundle", bundle);

        // 在新 task 中打开
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置跳转到广播
        alarmIntent.setClass(context, ActionNoticeReceiver.class)
                .setAction("norah1to.notification.notice")
                .setComponent(new ComponentName(
                        "com.norah1to.simplenotification",
                        "com.norah1to.simplenotification.BroadcastReceiver.ActionNoticeReceiver"
                        ));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                todo.getNoticeCode(),
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 根据开关来决定 取消 & 开启 提醒
        if (todo.getNotice() == Todo.STATE_NOTICE) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, todo.getNoticeTimeStamp().getTime(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
