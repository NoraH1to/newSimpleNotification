package com.norah1to.simplenotification.Converters;


import androidx.room.TypeConverter;

import com.alibaba.fastjson.JSON;
import com.norah1to.simplenotification.Entity.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String TaglistToTagjson(List<Tag> tagList) {
        return JSON.toJSONString(tagList);
    }

    @TypeConverter
    public static List<Tag> TagjsonToTaglist(String tagJson) {
        return JSON.parseArray(tagJson, Tag.class);
    }
}