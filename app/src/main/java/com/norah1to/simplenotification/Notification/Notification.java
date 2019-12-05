package com.norah1to.simplenotification.Notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.norah1to.simplenotification.Entity.Todo;

public class Notification {

    public static final String CHANNEL_ID = "981122";
    public static final String TODO_CHANNEL_ID = "Todo";
    public static final String FOREGROUND_CHANNEL_ID = "TodoForeground";

    private Todo myTodo;
    private Intent myIntent;
    private Bundle mbundle = new Bundle();
    private Action myAction;

    public Notification (Todo todo, Action action) {
        myTodo = todo;
        myAction = action;
        myIntent = new Intent();
        myIntent.putExtra(Todo.TAG, todo);
        mbundle.putString(Todo.TAG, todo.getTodoID());
        myIntent.putExtra("bundle", mbundle);
    }

    public Notification (Todo todo) {
        myTodo = todo;
        myIntent = new Intent();
        myIntent.putExtra(Todo.TAG, todo);
        mbundle.putString(Todo.TAG, todo.getTodoID());
        myIntent.putExtra("bundle", mbundle);
    }

    public Notification() {

    }

    public void doAction(Context context) {
        myAction.doAction(context, myTodo, myIntent);
    }

    public void setMyAction(Action myAction) {
        this.myAction = myAction;
    }

    public void setMyTodo(Todo myTodo) {
        if (this.myTodo == null) {
            this.myTodo = myTodo;
            initIntent();
        }
        this.myTodo = myTodo;
    }

    private void initIntent() {
        myIntent = new Intent();
        myIntent.putExtra(Todo.TAG, myTodo);
        mbundle.putString(Todo.TAG, myTodo.getTodoID());
        myIntent.putExtra("bundle", mbundle);
    }
}
