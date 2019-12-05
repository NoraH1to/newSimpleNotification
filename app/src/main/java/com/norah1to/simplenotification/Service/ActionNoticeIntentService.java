package com.norah1to.simplenotification.Service;

import android.app.Application;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.norah1to.simplenotification.BroadcastReceiver.ActionDoneReceiver;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Repository.TodoRepository;
import com.norah1to.simplenotification.Util.DateUtil;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ActionNoticeIntentService extends IntentService {

    private static String TAG = "ActionNoticeIntentService";

    public ActionNoticeIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         *  设置前台通知
         */
        Log.d(TAG, "onHandleIntent: setting foreground notification");
        Notification foregroundNotification;

        NotificationCompat.Builder foregroundbuilder = new NotificationCompat.Builder(
                this, com.norah1to.simplenotification.Notification.Notification.FOREGROUND_CHANNEL_ID
        );

        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    com.norah1to.simplenotification.Notification.Notification.FOREGROUND_CHANNEL_ID,
                    "前台服务通知", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
            foregroundbuilder.setChannelId(com.norah1to.simplenotification.Notification.Notification.FOREGROUND_CHANNEL_ID);
        }

        foregroundbuilder.setContentTitle("前台提醒推送服务");
        foregroundbuilder.setPriority(NotificationCompat.PRIORITY_MIN);

        foregroundNotification = foregroundbuilder.build();
        foregroundNotification.flags = NotificationCompat.FLAG_FOREGROUND_SERVICE;

        startForeground(Integer.parseInt(com.norah1to.simplenotification.Notification.Notification.CHANNEL_ID), foregroundNotification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String todoID = intent.getStringExtra(Todo.TAG);
        Log.d(TAG, "onHandleIntent: todoID: " + todoID);
        // 初始化 repository
        TodoRepository repository = new TodoRepository((Application) this.getApplicationContext());
        // 根据 id 获得真正的对象（发送通知后可能改变过数据库中的对象
        Todo realTodo = repository.getmTodoByID(todoID);


        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);


        /**
         *  设置提醒通知
         */
        Log.d(TAG, "onHandleIntent: setting notice");
        Notification notification;
        NotificationCompat.Builder builder;

        builder = new NotificationCompat.Builder(
                this, realTodo.getNoticeCode() + "");



        // 设置提示铃声为闹钟音量 // Todo: 蜜汁失效
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setLights(0, 0, 0);
        builder.setVibrate(new long[]{5000, 5000});
        builder.setSound(sound, AudioManager.STREAM_ALARM);


        /**
         *  安卓O以上申请通知通道
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    com.norah1to.simplenotification.Notification.Notification.CHANNEL_ID,
                    "提醒", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            notificationChannel.setShowBadge(true);
            AudioAttributes.Builder builder1 = new AudioAttributes.Builder();
            builder1.setUsage(AudioAttributes.USAGE_ALARM);
            notificationChannel.setSound(sound, builder1.build());
            notificationChannel.setLightColor(0);
            notificationChannel.setBypassDnd(true);
            notificationChannel.setVibrationPattern(new long[]{5000, 5000});
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId(com.norah1to.simplenotification.Notification.Notification.CHANNEL_ID);
        }


        /**
         *  封装一个通知点击的按钮事件
         */
        // 克隆 intent，修改目标
        Intent actionIntent = (Intent) intent.clone();
        actionIntent.setClass(this, ActionDoneReceiver.class);
        actionIntent.setAction("norah1to.notification.done");
        actionIntent.setComponent(
                new ComponentName("com.norah1to.simplenotification",
                        "com.norah1to.simplenotification.BroadcastReceiver.ActionDoneReceiver"));
        NotificationCompat.Action action = new NotificationCompat.Action(
                R.drawable.ic_done_grey_24dp,
                this.getString(R.string.notification_action_done),
                PendingIntent.getBroadcast(
                        this,
                        realTodo.getNoticeCode(),
                        actionIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT)
        );
        // 添加 action
        builder.addAction(action);


        // 设置持久悬浮提示
        builder.setFullScreenIntent(
                PendingIntent.getActivity(
                        this,
                        realTodo.getNoticeCode(),
                        new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT),
                true);


        /**
         *  设置内容
         */
        // 通知内容显示待做内容
        builder.setContentText(realTodo.getContent());
        // 通知标题显示时间
        builder.setContentTitle(DateUtil.formDatestr(realTodo.getNoticeTimeStamp()));
        // 状态栏小图标
        builder.setSmallIcon(R.drawable.ic_done_grey_24dp);
        // 可见最大
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        // 优先级最高
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        // 时间戳为提醒时间
        builder.setWhen(realTodo.getNoticeTimeStamp().getTime());
        // 点击后去掉
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_SOUND |
                NotificationCompat.DEFAULT_VIBRATE);
        notification = builder.build();
        notification.flags = NotificationCompat.FLAG_AUTO_CANCEL | NotificationCompat.FLAG_INSISTENT;

        /**
         *  推送通知
         */
        Log.d(TAG, "onHandleIntent: push notice");
        notificationManager.cancel(realTodo.getNoticeCode());
        notificationManager.notify(realTodo.getNoticeCode(), notification);
    }
}
