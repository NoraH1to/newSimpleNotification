package com.norah1to.simplenotification.BroadcastReceiver;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Notification.Action;
import com.norah1to.simplenotification.Notification.ActionCreateImpl;
import com.norah1to.simplenotification.Notification.ActionMakeAlarm;
import com.norah1to.simplenotification.Notification.Notification;
import com.norah1to.simplenotification.Repository.TodoRepository;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {

    public static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: bootsuccess");
        // Todo: 数据变多可能会导致广播超时，换成intentService来执行
        TodoRepository todoRepository = new TodoRepository(
                (Application) context.getApplicationContext());
        new Thread(() -> {
            List<Todo> todoList = todoRepository.getNoticeTodos();
            Notification notification = new Notification();
            Action action = new ActionCreateImpl();
            action = new ActionMakeAlarm(action);
            notification.setMyAction(action);
            for (Todo todo : todoList) {
                notification.setMyTodo(todo);
                notification.doAction(context);
            }
        }).start();
    }
}
