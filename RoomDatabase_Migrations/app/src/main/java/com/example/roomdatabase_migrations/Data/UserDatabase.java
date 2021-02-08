package com.example.roomdatabase_migrations.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabase_migrations.Model.User;
// đây là csdl gốc, version 1, nếu muốn thử các ván đề trong Learn thì cần thay đổi và cập nhật version
@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public static UserDatabase userDatabase;

    public static synchronized UserDatabase getUserDatabase(Context context){
        if (userDatabase == null){
            userDatabase = Room.databaseBuilder(
                    context,
                    UserDatabase.class,
                    "notes_db" // name database
            ).build();
        }
        return userDatabase;
    }

    public abstract UserDAO userDAO();
}
