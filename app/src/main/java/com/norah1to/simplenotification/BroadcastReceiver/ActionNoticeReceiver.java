package com.norah1to.simplenotification.BroadcastReceiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Util.DateUtil;

public class ActionNoticeReceiver extends BroadcastReceiver {

    public static final String TAG = "ActionNoticeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, TAG, Toast.LENGTH_SHORT).show();
        // Todo: fix
        Todo todo = (Todo) intent.getSerializableExtra(Todo.TAG);
        if (todo == null) {
            Log.d(TAG, "onReceive: todo is NULL");
        }
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
                    com.norah1to.simplenotification.Notification.Notification.CHANNEL_ID,
                    "提醒", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId(com.norah1to.simplenotification.Notification.Notification.CHANNEL_ID);
        }


        /**
         *  封装一个通知点击的按钮事件
         */
        // 克隆 intent，修改目标
        Intent actionIntent = (Intent) intent.clone();
        actionIntent.setClass(context, ActionDoneReceiver.class);
        actionIntent.setAction("norah1to.notification.done");
        actionIntent.setComponent(
                new ComponentName("com.norah1to.simplenotification",
                        "com.norah1to.simplenotification.BroadcastReceiver.ActionDoneReceiver"));
        NotificationCompat.Action action = new NotificationCompat.Action(
                R.drawable.ic_done_grey_24dp,
                context.getString(R.string.notification_action_done),
                PendingIntent.getBroadcast(
                        context,
                        todo.getNoticeCode(),
                        actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT)
        );
        // 添加 action
        builder.addAction(action);


        // 设置持久悬浮提示
        builder.setFullScreenIntent(
                PendingIntent.getActivity(
                        context,
                        todo.getNoticeCode(),
                        new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT),
                true);


        // 设置提示铃声为闹钟音量
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setDefaults(NotificationCompat.FLAG_INSISTENT);
        builder.setSound(sound);


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
        // 点击后去掉
        builder.setAutoCancel(true);

        notification = builder.build();


        /**
         *  推送通知
         */
        notificationManager.notify(todo.getNoticeCode(), notification);
    }
}
