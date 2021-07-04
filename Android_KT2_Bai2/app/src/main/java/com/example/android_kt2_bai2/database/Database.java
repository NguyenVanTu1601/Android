package com.example.android_kt2_bai2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_kt2_bai2.constants.Constants;
import com.example.android_kt2_bai2.model.Donation;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS qgop (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(200)," +
                "city VARCHAR(200)," +
                "date VARCHAR(200)," +
                "money INTEGER)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    // add
    public void addDonation(Donation donation){
        String sql = "INSERT INTO qgop(name, city,date, money) " +
                "VALUES (?, ?, ?, ?)";

        // mảng đối số thay thế vào chỗ ?
        String[] args = {donation.getName(), donation.getCity(), donation.getDate(),
                Integer.toString(donation.getMoney())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    // get list all
    public List<Donation> getListAll(){
        List<Donation> donations = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        //String sql = "SELECT * FROM qgop";
        //Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Cursor cursor = sqLiteDatabase.query("qgop", null,
                null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id      = cursor.getInt(0);
            String name = cursor.getString(1);
            String city = cursor.getString(2);
            String date = cursor.getString(3);
            int money   = cursor.getInt(4);

            Donation donation = new Donation(id, name, city, date, money);
            donations.add(donation);
        }

        return donations;
    }

    // search
    public List<Donation> getDonationByName(String name) {
        List<Donation> list = new ArrayList<>();
        String whereClause  = "name LIKE ?";
        String[] whereArgs = {"%" + name + "%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("qgop",
                null, whereClause, whereArgs, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int idD = cursor.getInt(cursor.getColumnIndex("id"));
            String nameD = cursor.getString(cursor.getColumnIndex("name"));
            String cityD = cursor.getString(cursor.getColumnIndex("city"));
            String dateD = cursor.getString(cursor.getColumnIndex("date"));
            int money = cursor.getInt(cursor.getColumnIndex("money"));

            list.add(new Donation(idD, nameD, cityD, dateD, money));
        }
        cursor.close();
        return list;
    }

    // UPDATE Student SET name = ?, gender = ?,mark=? WHERE id = ?
    public int updateDonation(Donation donation) {
        ContentValues values = new ContentValues();
        values.put("name", donation.getName());
        values.put("city", donation.getCity());
        values.put("date",donation.getDate());
        values.put("money",donation.getMoney());

        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(donation.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("qgop",
                values, whereClause, whereArgs);
    }

    // DELETE FROM Student WHERE id = ?
    public int deleteDonation(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("qgop",
                whereClause, whereArgs);
    }

}
