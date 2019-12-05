package com.norah1to.simplenotification.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.norah1to.simplenotification.Entity.Todo;

public class ActionRemoveAlarm extends ActionCancelImpl {
    Action action;

    public ActionRemoveAlarm(Action action) {
        this.action = action;
    }

    @Override
    public void doAction(Context context, Todo todo, Intent intent) {
        action.doAction(context, todo, intent);

        // 封装准备取消的提醒 pendingIntent
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(
                context,
                todo.getNoticeCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        // 获取提醒管理器
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);

        // 撤销提醒
        alarmManager.cancel(cancelPendingIntent);

        // 释放 pendingIntent
        cancelPendingIntent.cancel();
    }
}
