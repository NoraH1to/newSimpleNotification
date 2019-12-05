package com.norah1to.simplenotification.Util;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;

public class StringUtil {

    // 蓝色下划线样式
    public static SpannableStringBuilder setUrlParseStyle(String inputString) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append(inputString);
        // 颜色
        ForegroundColorSpan foregroundColorSpan =
                new ForegroundColorSpan(Color.parseColor("#009ad6"));
        spannableString.setSpan(
                foregroundColorSpan,
                0,
                inputString.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        // 下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(
                underlineSpan,
                0,
                inputString.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }
}
