package com.norah1to.simplenotification.Notification;

import android.content.Context;
import android.content.Intent;

import com.norah1to.simplenotification.Entity.Todo;

public class Notification {

    public static final String INTENT_KEY_TODO_OBJ = "INTENT_KEY_TODO_OBJ";

    private Todo myTodo;
    private Intent myIntent;
    private Action myAction;

    public Notification (Todo todo, Action action) {
        myTodo = todo;
        myAction = action;
        myIntent = new Intent();
        myIntent.putExtra(Notification.INTENT_KEY_TODO_OBJ, todo);
    }

    public Notification (Todo todo) {
        myTodo = todo;
        myIntent = new Intent();
        myIntent.putExtra(Notification.INTENT_KEY_TODO_OBJ, todo);
    }

    public void doAction(Context context) {
        myAction.doAction(context, myTodo, myIntent);
    }

    public void setMyAction(Action myAction) {
        this.myAction = myAction;
    }
}
