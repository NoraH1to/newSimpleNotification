package com.norah1to.simplenotification;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Util.ChipUtil;
import com.norah1to.simplenotification.ViewModel.TagViewModel;

public class TagActivity extends AppCompatActivity {

    public static final String TAG = "TagActivity";

    public static TagViewModel tagViewModel;

    private ChipGroup tagGroup;

    private TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);


        // 初始化 tags 显示组件
        tagGroup = (ChipGroup) findViewById(R.id.chip_group_tag);


        // 初始化添加 tag 的输入框
        textInputEditText = (TextInputEditText) findViewById(R.id.input_create_tag);
        // 监听回车
        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 监听输入时的回车键
                if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() &&
                        KeyEvent.ACTION_DOWN == event.getAction())) {
                    Tag tag = new Tag();
                    tag.setName(v.getText().toString());
                    tag.setUserID("testID"); // TODO: 替换成全局用户 ID
                    try {
                        tagViewModel.insert(tag);
                        tagGroup.addView(ChipUtil.createChip(v.getContext(), v.getText().toString(), getMenuCloseClickListener()));
                    } catch (Exception e) {
                        Log.d(TAG, "onEditorAction: " + e.toString());
                    }
                    // 清空输入框
                    v.setText(null);
                    return true;
                }
                return false;
            }
        });


        // 初始化 viewModel
        tagViewModel = ViewModelProviders.of(this).get(TagViewModel.class);
        // 监听所有 tag 的列表
        tagViewModel.getmAllTags().observe(this, mAllTags -> {
            tagGroup.removeAllViews();
            for (Tag tag : mAllTags) {
                tagGroup.addView(ChipUtil.createChip(this, tag.getName(), getMenuCloseClickListener()));
            }
        });
    }


    // 删除按钮点击回调
    private View.OnClickListener getMenuCloseClickListener () {
        return v -> {
            try {
                Thread thread = new Thread(() -> {
                    tagViewModel.deleteByName(((Chip) v).getText().toString());
                });
                thread.start();
                tagGroup.removeView(v);
            } catch (Exception e) {
                Log.d(TAG, "onDeleteClick: " + e.toString());
            }
        };
    }
}