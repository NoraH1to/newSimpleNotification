package com.norah1to.simplenotification.Notification;

import android.content.Context;
import android.content.Intent;

import com.norah1to.simplenotification.Entity.Todo;

public abstract class Action {
    public abstract void doAction(Context context, Todo todo, Intent intent);
}
