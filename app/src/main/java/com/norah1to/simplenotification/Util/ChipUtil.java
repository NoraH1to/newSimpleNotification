package com.norah1to.simplenotification.Util;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.chip.Chip;
import com.norah1to.simplenotification.R;

public class ChipUtil {
    // 根据文字返回对应的 chip 对象
    public static final Chip createChip(Context context, String text, View.OnClickListener listener) {

        // 设置文字样式
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_primary));
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
        spannableString.setSpan(colorSpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(blurMaskFilter, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        // 设置 chip 属性
        Chip chip = new Chip(context);
        // 显示删除图标
        chip.setCloseIconVisible(true);
        // 设置内容
        chip.setText(spannableString);
        // 监听删除按钮事件
        chip.setOnCloseIconClickListener(listener);
        return chip;
    }
}
