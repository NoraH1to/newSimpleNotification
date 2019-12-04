package com.norah1to.simplenotification.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.norah1to.simplenotification.Adapter.MyItemTouchHelperCallBack;
import com.norah1to.simplenotification.Adapter.TodoListAdapter;
import com.norah1to.simplenotification.Entity.User;
import com.norah1to.simplenotification.Http.HttpHelper;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Settings.SharePreferencesHelper;
import com.norah1to.simplenotification.ViewModel.TodoViewModel;

import java.util.Date;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private Handler handler = new Handler(Looper.getMainLooper());

    public static TodoViewModel mtodoViewModel;

    private boolean first = true;

    private RecyclerView recyclerView;
    private TodoListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private LinearLayoutCompat listBackground;

    private BottomAppBar bottomAppBar;

    private FloatingActionButton fab;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 初始化 todos 的 viewModel，监听 todos，实时更新列表数据
        mtodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        mtodoViewModel.getAllTodos().observe(this, todos -> {
            Log.d(TAG, "onCreate: updataList" + todos.size());
            if (todos.size() == 0) {
                listBackground.setVisibility(View.VISIBLE);
            } else {
                listBackground.setVisibility(View.GONE);
            }
            adapter.setTodos(todos);
        });


        // 初始化列表空背景
        listBackground = (LinearLayoutCompat) findViewById(R.id.linelayout_main_list_background);


        // 初始化列表
        recyclerView = (RecyclerView)findViewById(R.id.list_main);
        // 设置适配器
        adapter = new TodoListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setmProxy(new TodoListAdapter.Proxy() {
            @Override
            public void mStartActionMode(View view) {
                if (adapter.actionModeState == TodoListAdapter.STATE_ACTION_MODE_OFF) {
                    startSupportActionMode(new ActionModeCallBack());
                    adapter.setActionModeState(TodoListAdapter.STATE_ACTION_MODE_ON);
                    Log.d(TAG, "mStartActionMode: " + "start");
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });


        // 初始化 itemTouchHelper
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallBack(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        // 初始化下部底栏
        bottomAppBar = (BottomAppBar)findViewById(R.id.bottom_bar);


        // 初始化 fab
        fab = (FloatingActionButton)findViewById(R.id.fab_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // 设置列表布局管理器
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        // 监听 BottomAppBar 菜单点击
        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_app_bar_menu_search:
                    return true;
                case R.id.bottom_app_bar_menu_tags:
                    Intent intent1 = new Intent(this, TagActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.bottom_app_bar_menu_done:
                    Intent intent2 = new Intent(this, FinishTodosActivity.class);
                    startActivity(intent2);
                    return true;
                default:
                    return false;
            }
        });
        // 监听 BottomAppBar navigationitem 点击
        bottomAppBar.setNavigationOnClickListener(v -> {
            FragmentManager fragmentManager =  this.getSupportFragmentManager();
            MainSheetDialogFragment mainSheetDialogFragment = new MainSheetDialogFragment();
            mainSheetDialogFragment.show(fragmentManager, "233");
            fragmentManager.beginTransaction();
        });


        // 监听 fab 点击
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, MakeTodoActivity.class);
            startActivity(intent);
        });


        // 初始化 swipeReflashLayout
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_reflash_main);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(
                this,
                R.color.color_primary
        ));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Thread(() -> {
                // sleep 3s 如果刚刚登入完因为插入用户是异步操作，可能会不能立刻获取到用户
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User tmpUser = userViewModel.getmUser().getValue();
                if (SharePreferencesHelper.getUserState(this) == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    handler.post(() -> {
                        Toast.makeText(this, "请先登入", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    });
                    startActivity(intent);
                    return;
                }
                long time = tmpUser.getLastSyncTimestamp().getTime();
                HttpHelper.ResultBean resultBean = HttpHelper.syncData(handler,
                        mtodoViewModel.getCreatedTodos(time),
                        mtodoViewModel.getModifiedTodos(time),
                        mtodoViewModel.getDeletedTodos(time),
                        mtodoViewModel.getCreatedTags(time),
                        mtodoViewModel.getModifiedTags(time),
                        mtodoViewModel.getDeletedTags(time));
                swipeRefreshLayout.setRefreshing(false);
                handler.post(() -> {
                    if (resultBean.isSuccess()) {
                        tmpUser.setLastSyncTimestamp(new Date());
                        userViewModel.insert(tmpUser);
                        Toast.makeText(this, "同步成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "同步失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // actionMode 钩子函数覆写
    private class ActionModeCallBack implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            fab.hide();
            bottomAppBar.setVisibility(View.INVISIBLE);
            swipeRefreshLayout.setPadding(0, 0, 0, 0);
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
            adapter.setActionModeState(TodoListAdapter.STATE_ACTION_MODE_OFF);
            swipeRefreshLayout.setPaddingRelative(0, 0, 0,
                    (int)(getApplicationContext().getResources().getDisplayMetrics().density*56 +0.5f));
            fab.show();
            bottomAppBar.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setEnabled(true);
        }
    }
}