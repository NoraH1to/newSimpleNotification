package com.norah1to.simplenotification.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.norah1to.simplenotification.Converters;
import com.norah1to.simplenotification.Dao.TodoDao;
import com.norah1to.simplenotification.Entity.Todo;

@Database(entities = {Todo.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TodoRoomDataBase extends RoomDatabase {

    public abstract TodoDao todoDao();

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
