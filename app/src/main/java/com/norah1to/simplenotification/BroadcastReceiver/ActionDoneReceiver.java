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

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.Date;

public class ActionDoneReceiver extends BroadcastReceiver {

    public static final String TAG = "ActionDoneReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获得传入TodoID
        String todoID = intent.getBundleExtra("bundle").getString(Todo.TAG);
        // 初始化 repository
        TodoRepository todoRepository = new TodoRepository((Application) context.getApplicationContext());
        new Thread(() -> {
            // 根据 id 获得真正的对象（发送通知后可能改变过数据库中的对象
            Todo realTodo = todoRepository.getmTodoByID(todoID);
            new Handler(context.getMainLooper()).post(() -> {
                // 获取通知管理器
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                // 撤销通知
                notificationManager.cancel(realTodo.getNoticeCode());

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        context,
                        realTodo.getNoticeCode(),
                        new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // 撤销提醒（不管有没有
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                // 撤销通知
                pendingIntent.cancel();
            });
            realTodo.setCompletedTimeStamp(new Date());
            todoRepository.update(realTodo);
        }).start();
    }
}
