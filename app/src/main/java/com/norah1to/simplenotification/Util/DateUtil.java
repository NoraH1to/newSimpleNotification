package com.norah1to.simplenotification.Util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String formDatestr(Date date) {

        String formatStr;

        // 简单判断是不是当年
        if (date.getYear() == (new Date(System.currentTimeMillis())).getYear()) {
            formatStr = "MM月dd日，HH:mm";
        } else {
            formatStr = "yyyy年MM月dd日，HH:MM";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
//        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return sdf.format(date);
    }
}
