package com.norah1to.simplenotification.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Service.ActionNoticeIntentService;

public class ActionMakeAlarm extends ActionCreate {

    public static final String TAG = "ActionMakeAlarm";

    Action action;

    public ActionMakeAlarm(Action action) {
        this.action = action;
    }

    @Override
    public void doAction(Context context, Todo todo, Intent intent) {
        action.doAction(context, todo, intent);

        // 小于当前时间的提醒不作处理 // todo: 生产环境删除
//        if (todo.getNoticeTimeStamp().getTime() <= System.currentTimeMillis()) {
//            return;
//        }
        // 克隆 intent
        Intent alarmIntent = (Intent) intent.clone();

        // 设置跳转到广播
        alarmIntent.setClass(context, ActionNoticeIntentService.class);
        // MIUI 上不知道为啥，只能拿到这个 name 下面的 String 值，我吐了
        alarmIntent.putExtra(Todo.TAG, todo.getTodoID());

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getForegroundService(
                    context,
                    todo.getNoticeCode(),
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getService(
                    context,
                    todo.getNoticeCode(),
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
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
