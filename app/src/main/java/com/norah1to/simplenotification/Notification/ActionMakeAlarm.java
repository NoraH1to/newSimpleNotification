package com.norah1to.simplenotification.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
//        if (todo.getNoticeTimeStamp().getTime() <= System.currentTimeMillis()) {
//            return;
//        }
        // Todo: fix alarm
        Intent alarmIntent = (Intent) intent.clone();
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.setClass(context, ActionNoticeReceiver.class) // todo: 改变提醒跳转
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
        Log.d(TAG, "doAction: todoNoticeCode: " + ((Todo)intent.getSerializableExtra(Todo.TAG)).getNoticeCode());
        if (todo.getNotice() == Todo.STATE_NOTICE) {
            Log.d(TAG, "doAction: \n todotime: " + todo.getNoticeTimeStamp().getTime() + "\n" +
                    "systime: " + System.currentTimeMillis());
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, todo.getNoticeTimeStamp().getTime(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
