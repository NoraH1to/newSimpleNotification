package com.norah1to.simplenotification.Converters;


import androidx.room.TypeConverter;

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
    public static String TaglistToTagjson(List<String> tagList) {
        // TODO: list to json
        return "";
    }

    @TypeConverter
    public static List<String> TagjsonToTaglist(String tagJson) {
        List<String> result = new ArrayList<String>();
        // TODO: json to list
        return result;
    }
}