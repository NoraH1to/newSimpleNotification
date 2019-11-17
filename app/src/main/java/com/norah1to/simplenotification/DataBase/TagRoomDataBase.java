package com.norah1to.simplenotification.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.norah1to.simplenotification.Dao.TagDao;
import com.norah1to.simplenotification.Entity.Tag;

@Database(entities = {Tag.class}, version = 1, exportSchema = false)
//@TypeConverters({Converters.class})
public abstract class TagRoomDataBase extends RoomDatabase {

    public abstract TagDao tagDao();

    private static volatile TagRoomDataBase INSTANCE;

    public static TagRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TagRoomDataBase.class, "tag_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}