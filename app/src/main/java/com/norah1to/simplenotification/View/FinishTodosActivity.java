package com.norah1to.simplenotification.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.norah1to.simplenotification.Adapter.FinishTodoListAdapter;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.ViewModel.FinishTodoViewModel;

public class FinishTodosActivity extends AppCompatActivity {

    public static final String TAG = "FinishTodosActivity";

    private Handler handler = new Handler(Looper.getMainLooper());

    private RecyclerView recyclerView;

    private LinearLayoutCompat listBackground;

    private FinishTodoListAdapter adapter;

    private static FinishTodoViewModel mFinishTodoViewModel;

    private LinearLayoutManager linearLayoutManager;

    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_todos);


        // 初始化 viewmodel
        mFinishTodoViewModel = ViewModelProviders.of(this).get(FinishTodoViewModel.class);
        mFinishTodoViewModel.getAllTodos().observe(this, todos -> {
            Log.d(TAG, "onCreate: ");
            if (todos.size() == 0) {
                listBackground.setVisibility(View.VISIBLE);
            } else {
                listBackground.setVisibility(View.GONE);
            }
            adapter.setTodos(todos);
        });


        // 初始化适配器
        adapter = new FinishTodoListAdapter(this);


        // 初始化列表空背景
        listBackground = (LinearLayoutCompat) findViewById(R.id.linelayout_finish_todo_list_background);


        // 初始化列表
        recyclerView = (RecyclerView) findViewById(R.id.list_finish_todo);
        adapter.setmProxy(new FinishTodoListAdapter.Proxy() {
            @Override
            public void mStartActionMode(View view) {
                if (adapter.actionModeState == FinishTodoListAdapter.STATE_ACTION_MODE_OFF) {
                    startSupportActionMode(new FinishTodosActivity.ActionModeCallBack());
                    adapter.setActionModeState(FinishTodoListAdapter.STATE_ACTION_MODE_ON);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 设置列表布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    // actionMode 钩子函数覆写
    private class ActionModeCallBack implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.main_action_mode_menu, menu);
            mode.setTitle("多选操作");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menuitem_main_action_mode_delete:
                    adapter.deleteSeletedItems(handler);
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.setActionModeState(FinishTodoListAdapter.STATE_ACTION_MODE_OFF);
        }
    }
}
