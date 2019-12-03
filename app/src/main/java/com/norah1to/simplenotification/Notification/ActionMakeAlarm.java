package com.norah1to.simplenotification.Notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.View.MainActivity;

public class ActionMakeAlarm extends ActionCreate {

    public static final String TAG = "ActionMakeAlarm";

    Action action;

    public ActionMakeAlarm(Action action) {
        this.action = action;
    }

    @Override
    public void doAction(Context context, Todo todo, Intent intent) {
        action.doAction(context, todo, intent);
        // Todo: 创建 alarm
        intent.setClass(context, MainActivity.class); // todo: 改变提醒跳转
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, todo.getNoticeCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (todo.getNotice() == Todo.STATE_NOTICE) {
            Log.d(TAG, "doAction: ");
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, todo.getNoticeTimeStamp().getTime(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
