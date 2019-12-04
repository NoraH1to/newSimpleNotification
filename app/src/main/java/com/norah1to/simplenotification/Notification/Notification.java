package com.norah1to.simplenotification.Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.norah1to.simplenotification.Entity.Todo;

public class Notification {

    public static final String CHANNEL_ID = "981122";

    private Todo myTodo;
    private Intent myIntent;
    private Bundle mbundle = new Bundle();
    private Action myAction;

    public Notification (Todo todo, Action action) {
        myTodo = todo;
        myAction = action;
        myIntent = new Intent();
        myIntent.putExtra(Todo.TAG, todo);
        mbundle.putSerializable(Todo.TAG, todo);
        myIntent.putExtra("bundle", mbundle);
    }

    public Notification (Todo todo) {
        myTodo = todo;
        myIntent = new Intent();
        myIntent.putExtra(Todo.TAG, todo);
        mbundle.putSerializable(Todo.TAG, todo);
        myIntent.putExtra("bundle", mbundle);
    }

    public void doAction(Context context) {
        myAction.doAction(context, myTodo, myIntent);
    }

    public void setMyAction(Action myAction) {
        this.myAction = myAction;
    }
}
