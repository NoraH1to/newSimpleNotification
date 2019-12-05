package com.norah1to.simplenotification.Notification;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.norah1to.simplenotification.Entity.Todo;

public class ActionRemoveNotification extends ActionCancelImpl {

    Action action;

    public ActionRemoveNotification(Action action) {
        this.action = action;
    }

    @Override
    public void doAction(Context context, Todo todo, Intent intent) {
        action.doAction(context, todo, intent);

        // 获得通知管理器
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 撤销通知
        notificationManager.cancel(todo.getNoticeCode());
    }
}
