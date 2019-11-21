package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.norah1to.simplenotification.Adapter.TodoListAdapter;
import com.norah1to.simplenotification.Settings.SettingsActivity;
import com.norah1to.simplenotification.ViewModel.TodoViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static TodoViewModel mtodoViewModel;

    private boolean first = true;

    private RecyclerView recyclerView;
    private TodoListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private BottomAppBar bottomAppBar;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 初始化 todos 的 viewModel，监听 todos，实时更新列表数据
        mtodoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        mtodoViewModel.getAllTodos().observe(this, todos -> {
            if (first){
                adapter.setTodos(todos);
                first = false;
            } else if (adapter.getItemCount() == todos.size()) {
                adapter.setTodos(todos);
            } else {
                adapter.addTodo(todos.get(0));
                recyclerView.scrollToPosition(0);
            }
//            adapter.setTodos(todos);
        });


        // 初始化列表
        recyclerView = (RecyclerView)findViewById(R.id.list_main);
        // 设置适配器
        adapter = new TodoListAdapter(this);
        recyclerView.setAdapter(adapter);


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
                case R.id.bottom_app_bar_menu_settings:
                    Intent intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.bottom_app_bar_menu_tags:
                    Intent intent1 = new Intent(this, TagActivity.class);
                    startActivity(intent1);
                    return true;
                default:
                    return false;
            }
        });


        // 监听 fab 点击
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, MakeTodoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}