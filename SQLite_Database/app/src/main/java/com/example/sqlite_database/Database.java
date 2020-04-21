package com.example.sqlite_database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Tạo function truy vấn không trả về kết quả (CREATE, INSERT , DELETE, UPDATE...)
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase(); // tương tác với database
        database.execSQL(sql); // thực thi lệnh
    }

    // Tạo function lấy kết quả dạng con trỏ (Cursor)
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase(); // Đọc thông tin, nếu dùng getWrite có thê vừa đọc vừa ghi
        return database.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
