package com.norah1to.simplenotification.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.norah1to.simplenotification.Converters.Converters;
import com.norah1to.simplenotification.Dao.TagDao;
import com.norah1to.simplenotification.Dao.TodoDao;
import com.norah1to.simplenotification.Dao.UserDao;
import com.norah1to.simplenotification.Entity.Tag;
import com.norah1to.simplenotification.Entity.Todo;
import com.norah1to.simplenotification.Entity.User;

@Database(entities = {Todo.class, Tag.class, User.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TodoRoomDataBase extends RoomDatabase {

    public abstract TodoDao todoDao();
    public abstract TagDao tagDao();
    public abstract UserDao userDao();

    private static volatile TodoRoomDataBase INSTANCE;

    public static TodoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDataBase.class, "todo_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
