package com.norah1to.simplenotification.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Util.DateUtil;
import com.norah1to.simplenotification.View.MakeTodoActivity;

public class ActionMakeNotification extends ActionCreate {

    Action action;

    public ActionMakeNotification(Action action) {
        this.action = action;
    }

    @Override
    public void doAction(Context context, Todo todo, Intent intent) {
        action.doAction(context, todo, intent);

        Notification notification;
        NotificationCompat.Builder builder;

        builder = new NotificationCompat.Builder(
                context, todo.getNoticeCode() + "");

        NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        /**
         *  安卓O以上申请通知通道
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    com.norah1to.simplenotification.Notification.Notification.TODO_CHANNEL_ID,
                    "Todo", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId(com.norah1to.simplenotification.Notification.Notification.TODO_CHANNEL_ID);
        }


        /**
         *  封装一个通知点击的按钮事件
          */
        // 克隆 intent，修改目标
        Intent actionIntent = (Intent) intent.clone();
        actionIntent.setAction("norah1to.notification.done");
        actionIntent.setComponent(
                new ComponentName("com.norah1to.simplenotification",
                        "com.norah1to.simplenotification.BroadcastReceiver.ActionDoneReceiver"));
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(
                context,
                todo.getNoticeCode(),
                actionIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action(
                R.drawable.ic_done_grey_24dp,
                context.getString(R.string.notification_action_done),
                actionPendingIntent
        );
        // 添加 action
        builder.addAction(action);


        /**
         *  封装点击跳转事件
         */
        Intent clickIntent = (Intent) intent.clone();
        clickIntent.setClass(context, MakeTodoActivity.class);
        clickIntent.putExtra(Todo.TAG, todo);
        // 添加点击事件
        PendingIntent clickPendingIntent = PendingIntent.getActivity(
                context,
                todo.getNoticeCode(),
                clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(clickPendingIntent);


        /**
         *  设置内容
         */
        // 通知内容显示待做内容
        builder.setContentText(todo.getContent());
        // 通知标题显示时间
        builder.setContentTitle(DateUtil.formDatestr(todo.getNoticeTimeStamp()));
        // 状态栏小图标
        builder.setSmallIcon(R.drawable.ic_done_grey_24dp);// todo: 修改图标
        // 可见最大
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        // 优先级最高
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        // 时间戳为提醒时间
        builder.setWhen(todo.getNoticeTimeStamp().getTime());
        // 可以手动划掉
        builder.setAutoCancel(true);

        notification = builder.build();
        //        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags = NotificationCompat.FLAG_AUTO_CANCEL;


        /**
         *  推送通知
         */
        notificationManager.notify(todo.getNoticeCode(), notification);
    }
}
