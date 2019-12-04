package com.norah1to.simplenotification.BroadcastReceiver;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.Date;

public class ActionDoneReceiver extends BroadcastReceiver {

    public static final String TAG = "ActionDoneReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获得传入Todo
        Todo mTodo = (Todo) intent.getSerializableExtra(Todo.TAG);
        // 初始化 repository
        TodoRepository todoRepository = new TodoRepository((Application) context.getApplicationContext());
        // 设置完成并且更新数据库
        mTodo.setCompletedTimeStamp(new Date());
        new Thread(() -> {
            todoRepository.update(mTodo);
        }).start();
        // 获取通知管理器
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 撤销通知
        notificationManager.cancel(mTodo.getNoticeCode());
        PendingIntent.getBroadcast(
                context,
                mTodo.getNoticeCode(),
                new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT).cancel();
    }
}
