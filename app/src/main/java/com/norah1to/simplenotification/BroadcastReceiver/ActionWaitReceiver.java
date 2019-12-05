package com.norah1to.simplenotification.BroadcastReceiver;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Notification.Action;
import com.norah1to.simplenotification.Notification.ActionCreateImpl;
import com.norah1to.simplenotification.Notification.ActionMakeAlarm;
import com.norah1to.simplenotification.Notification.Notification;
import com.norah1to.simplenotification.Repository.TodoRepository;
import com.norah1to.simplenotification.Settings.SharePreferencesHelper;

import java.util.Calendar;
import java.util.Date;

public class ActionWaitReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Todo: 添加稍后完成的内容
        TodoRepository todoRepository = new TodoRepository(
                (Application) context.getApplicationContext());
        // 获得传入的todoID
        String todoID = intent.getBundleExtra("bundle").getString(Todo.TAG);
        new Thread(() -> {
            // 根据 id 获得真正的对象（发送通知后可能改变过数据库中的对象
            Todo realTodo = todoRepository.getmTodoByID(todoID);
            // 当前时间 + 设置中的等待时长
            Date tmpDate = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(tmpDate);
            c.add(Calendar.MINUTE, SharePreferencesHelper.getWaitNoticeTimeMin(context));
            // 更新todo中的提醒时间
            realTodo.setNoticeTimeStamp(c.getTime());
            new Handler(context.getMainLooper()).post(() -> {
                // 获取通知管理器
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                // 撤销通知
                notificationManager.cancel(realTodo.getNoticeCode());
            });
            // 创建提醒
            Notification notification = new Notification(realTodo);
            Action action = new ActionCreateImpl();
            action = new ActionMakeAlarm(action);
            notification.setMyAction(action);
            notification.doAction(context);
        }).start();
    }
}
