package com.example.test_listview_with_editext.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.test_listview_with_editext.DAO.LonganDAO;
import com.example.test_listview_with_editext.Entities.Longan;

@Database(entities = Longan.class, version = 1, exportSchema = false)
public abstract class LonganDatabase extends RoomDatabase {
    public static LonganDatabase longanDatabase;

    public static synchronized LonganDatabase getLonganDatabase(Context context){
        if (longanDatabase == null){
            longanDatabase = Room.databaseBuilder(
                    context,
                    LonganDatabase.class,
                    "notes_db"
            ).build();
        }
        return longanDatabase;
    }

    public abstract LonganDAO longanDAO();
}
