package com.example.android_b09_sqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_b09_sqlite.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLStudentOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB.db";
    private static final int VERSION = 1;

    public SQLStudentOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(200)," +
                "gender BOOLEAN," +
                "mark REAL)";
        sqLiteDatabase.execSQL(sql);
    }

    // mở database
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          int oldVersion, int newVersion) {

    }

    // add new student - insert into
    public void addStudent(Student student){
        String sql = "INSERT INTO student(name, gender,mark) " +
                     "VALUES (?,?,?)";
        // mảng đối số thay thế vào chỗ ?

        String[] args = {student.getName(), Boolean.toString(student.isGender()),
        Double.toString(student.getMark())};

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    // Cách 2
    public long addStudent2(Student student){
        ContentValues v = new ContentValues();
        v.put("name", student.getName());
        v.put("gender", student.isGender());
        v.put("mark", student.getMark());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("student", null, v);
    }

    // getStudentById
    public Student getStudent(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("student", null,
                whereClause, whereArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int studentId = cursor.getInt(cursor.getColumnIndex("id"));
            String studentName = cursor.getString(cursor.getColumnIndex("name"));
            boolean studentGender = cursor.getString(cursor.getColumnIndex("gender")).equals("TRUE");
            double studentMark=cursor.getDouble(cursor.getColumnIndex("mark"));
            cursor.close();
            return new Student(studentId, studentName, studentGender,studentMark);
        }
        return null;
    }

    // read all student - select * from
    public List<Student> getAll(){
        List<Student> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM student";
        //Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        Cursor cursor = sqLiteDatabase.query("student", null,
                null, null, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            boolean gender = cursor.getString(2).equals("TRUE");
            double mark = cursor.getDouble(3);
            Student st = new Student(id,name,gender,mark);
            list.add(st);
        }
        return list;
    }


}
