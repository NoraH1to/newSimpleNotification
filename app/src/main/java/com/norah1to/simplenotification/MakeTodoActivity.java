package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Util.DateUtil;
import com.norah1to.simplenotification.ViewModel.MakeTodoViewModel;
import com.norah1to.simplenotification.ViewModel.TodoViewModel;

import java.util.Calendar;
import java.util.Date;

public class MakeTodoActivity extends AppCompatActivity {

    private static final String TAG = "MakeTodoActivity";

    private MakeTodoViewModel makeTodoViewModel;
    private TodoViewModel todoViewModel;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

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


        // 初始化自己的 viewModel
        makeTodoViewModel = ViewModelProviders.of(this).get(MakeTodoViewModel.class);
        // 监听 mData 变化
        makeTodoViewModel.getmData().observe(this, mData -> {
            // 更改显示的内容
            dateTextView.setText(new StringBuilder().
                    append(getResources().getString(R.string.prefix_text_maketodo_date)).
                    append(DateUtil.formDatestr(mData)).toString());
        });
        // 监听 mTodo 的变化
        makeTodoViewModel.getmTodo().observe(this, mTodo -> {
            // 显示更改的内容
            String tmpInputStr = contentInput.getText().toString();
            if (tmpInputStr.equals("")) {
                contentInput.setText(mTodo.getContent());
            }
            // 更改通知图标
            ActionMenuItemView menuItemNotice =
                    (ActionMenuItemView) findViewById(R.id.menuitem_maketodo_notice);
            if (mTodo.getNotice() == Todo.STATE_NOTICE) {
                menuItemNotice.setIcon(ContextCompat.
                        getDrawable(this, R.drawable.ic_notifications_black_24dp));
            } else {
                menuItemNotice.setIcon(ContextCompat.
                        getDrawable(this, R.drawable.ic_notifications_off_grey_24dp));
            }
            // 修改优先级图标
            ActionMenuItemView menuItemPriority =
                    (ActionMenuItemView) findViewById(R.id.menuitem_maketodo_priority);
            switch (mTodo.getPriority()) {
                case Todo.PROIORITY_HIGH:
                    menuItemPriority.setIcon(ContextCompat.
                            getDrawable(this, R.drawable.ic_priority_pink_24dp));
                    break;
                case Todo.PROIORITY_MID:
                    menuItemPriority.setIcon(ContextCompat.
                            getDrawable(this, R.drawable.ic_priority_orange_24dp));
                    break;
                case Todo.PROIORITY_LOW:
                    menuItemPriority.setIcon(ContextCompat.
                            getDrawable(this, R.drawable.ic_priority_gray_24dp));
                    break;
                default:
                    menuItemPriority.setIcon(ContextCompat.
                            getDrawable(this, R.drawable.ic_priority_gray_24dp));
                    break;
            }
        });


        // 获得 MainActivity 中的 viewModel
        if (MainActivity.mtodoViewModel != null) {
            todoViewModel = MainActivity.mtodoViewModel;
        } else {
            todoViewModel = new TodoViewModel(getApplication());
        }


        // 如果是修改原有的，则根据 id 初始化内容
        Intent todoData = getIntent();
        if (todoData != null && todoData.getStringExtra(Todo.TAG) != null) {
            String todoID = todoData.getStringExtra(Todo.TAG);
            new Thread(() -> {
                makeTodoViewModel.setmTodo(todoID);
            }).start();
        } else {
            makeTodoViewModel.getmTodo().setValue(new Todo());
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onStart() {
        super.onStart();


        // 初始化 date，默认为明天
        if (makeTodoViewModel.getmData().getValue() == null) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, 1);
            makeTodoViewModel.getmData().setValue(c.getTime());
        }


        // 输入框获取焦点
        contentInput.requestFocus();


        // 监听 fab 点击
        fab.setOnClickListener(v -> {
            makeTodo();
        });


        // 监听 bottomBar 菜单点击
        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                // 设置时间的 pickerDialog
                case R.id.menuitem_maketodo_setdate:
                    showDatePickerDialog();
                    return true;
                case R.id.menuitem_maketodo_notice:
                    changeNoticeState();
                    return true;
                case R.id.menuitem_maketodo_settime:
                    showTimePickerDialog();
                    return true;
                case R.id.menuitem_maketodo_priority:
                    changePriority();
                    return true;
                default:
                    return false;
            }
        });
    }


    // 创建或者更新一个 todoObj
    private void makeTodo() {
        Todo todo = makeTodoViewModel.getmTodo().getValue();
        if (todo == null) {
            todo = new Todo();
        }
        todo.setContent(contentInput.getText().toString());
        todo.setNoticeTime(makeTodoViewModel.getmData().getValue());
        if (todo.getCreateTime() == null)
            todo.setCreateTime(new Date());
        todo.setUserID("testID");
        todoViewModel.insert(todo);
        finish();
    }


    // 显示一个日期选择器
    private void showDatePickerDialog() {
        Date tmpDate = makeTodoViewModel.getmData().getValue();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tmpDate.setYear(year - 1900);
                tmpDate.setMonth(month);
                tmpDate.setDate(dayOfMonth);
                makeTodoViewModel.getmData().setValue(tmpDate);
            }
        }, tmpDate.getYear() + 1900, tmpDate.getMonth(), tmpDate.getDate());
        datePickerDialog.show();
    }


    // 显示一个时间选择器
    private void showTimePickerDialog() {
        Date tmpDate = makeTodoViewModel.getmData().getValue();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tmpDate.setHours(hourOfDay);
                tmpDate.setMinutes(minute);
                makeTodoViewModel.getmData().setValue(tmpDate);
            }
        }, tmpDate.getHours(), tmpDate.getMinutes(), false);
        timePickerDialog.show();
    }


    // 添加、移除提醒
    private void changeNoticeState() {
        Todo tmpTodo = makeTodoViewModel.getmTodo().getValue();
        tmpTodo.setNotice(tmpTodo.getNotice() == Todo.STATE_NOTICE ?
                Todo.STATE_NOT_NOTICE : Todo.STATE_NOTICE);
        makeTodoViewModel.getmTodo().setValue(tmpTodo);
        // TODO: 提醒添加、移除的逻辑
    }

    // 切换优先级
    private void changePriority() {
        Todo tmpTodo = makeTodoViewModel.getmTodo().getValue();
        int selectedIndex = 3 - tmpTodo.getPriority() / 50;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        CharSequence[] characters = {"高", "中", "低"};
        builder.setSingleChoiceItems(characters, selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tmpTodo.setPriority((3 - which) * 50);
                makeTodoViewModel.getmTodo().setValue(tmpTodo);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
