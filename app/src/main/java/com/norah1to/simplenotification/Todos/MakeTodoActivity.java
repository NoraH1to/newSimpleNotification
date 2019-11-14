package com.norah1to.simplenotification.Todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.MainActivity;
import com.norah1to.simplenotification.R;
import com.norah1to.simplenotification.Util.DateUtil;
import com.norah1to.simplenotification.ViewModel.MakeTodoViewModel;
import com.norah1to.simplenotification.ViewModel.TodoViewModel;

import java.util.Calendar;
import java.util.Date;

public class MakeTodoActivity extends AppCompatActivity {

    private static final String TAG = "MakeTodoActivity";

    private MakeTodoViewModel makeTodoViewModel;
    private TodoViewModel todoViewModel;
    private Todo todo = null;

    private DatePickerDialog datePickerDialog;

    private TextInputEditText contentInput;

    private FloatingActionButton fab;

    private MaterialTextView dateTextView;

    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_todo);


        // 初始化 textinput
        contentInput = (TextInputEditText)findViewById(R.id.text_maketodo_content);


        // 初始化 fab
        fab = (FloatingActionButton)findViewById(R.id.fab_maketodo);


        // 初始化日期显示
        dateTextView = (MaterialTextView)findViewById(R.id.text_maketodo_date);


        // 初始化底栏
        bottomAppBar = (BottomAppBar)findViewById(R.id.bottom_bar_maketodo);


        // 尝试接收外部传入的数据
        // TODO: 1
        Intent todoData = getIntent();
        if (todoData != null) {
            if (todoData.getSerializableExtra(Todo.TAG) != null) {
                todo = (Todo)todoData.getSerializableExtra(Todo.TAG);
            }
        }

        if (todo == null) {
            todo = new Todo();
        }

        // 初始化自己的 viewModel
        makeTodoViewModel = ViewModelProviders.of(this).get(MakeTodoViewModel.class);
        // 监听 mData 变化
        makeTodoViewModel.getmData().observe(this, mData -> {
            // 更改显示的内容
            dateTextView.setText(DateUtil.formDatestr(mData));
            todo.setDate(mData);
        });

        // 获得 MainActivity 中的 viewModel
        if (MainActivity.mtodoViewModel != null) {
            todoViewModel = MainActivity.mtodoViewModel;
        } else {
            todoViewModel = new TodoViewModel(getApplication());
        }

    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onStart() {
        super.onStart();

        // 初始化 date，默认为明天
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 1);
        makeTodoViewModel.getmData().setValue(c.getTime());


        // 输入框获取焦点
        contentInput.requestFocus();


        // 监听 fab 点击
        fab.setOnClickListener(v -> {
            makeTodo();
        });


        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuitem_maketodo_setdate:
                    Date tmpDate = new Date(System.currentTimeMillis());
                    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            makeTodoViewModel.getmData().setValue(new Date(year - 1900, month, dayOfMonth));
                        }
                    }, tmpDate.getYear() + 1900, tmpDate.getMonth(), tmpDate.getDate());
                    datePickerDialog.show();
                    return true;
                default:
                    return false;
            }
        });
    }

    // 创建或者更新一个 todoObj
    private void makeTodo() {
        todo.setContent(contentInput.getText().toString());
        todo.setDate(makeTodoViewModel.getmData().getValue());
        todo.setCreateDate(new Date());
        todoViewModel.insert(todo);
        finish();
    }
}
