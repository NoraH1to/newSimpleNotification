package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.ViewModel.TagViewModel;

public class TagActivity extends AppCompatActivity {

    public static final String TAG = "TagActivity";

    private TagViewModel tagViewModel;

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
                if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    try {
                        tagGroup.addView(createChip(v.getText().toString()));
                    } catch (Exception e) {
                        Log.d(TAG, "onEditorAction: " + e.toString());
                    }

                    v.setText(null);
                    return true;
                }
                return false;
            }
        });
    }


    // 根据文字返回对应的 chip 对象
    private Chip createChip(String text) {

        // 设置文字样式
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_primary));
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
        spannableString.setSpan(colorSpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(blurMaskFilter, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // 设置 chip 属性
        Chip chip = new Chip(this);
        // 显示删除图标
        chip.setCloseIconVisible(true);
        // 设置内容
        chip.setText(spannableString);
        // 监听删除按钮事件
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 删除 tag
                tagGroup.removeView(v);
            }
        });
        return chip;
    }
}
