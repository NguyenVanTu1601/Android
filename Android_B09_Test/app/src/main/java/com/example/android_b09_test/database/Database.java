package com.example.android_b09_test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.android_b09_test.constants.Constants;
import com.example.android_b09_test.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS student (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(200)," +
                "gender INTEGER," +
                "mark FLOAT)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void addStudent(Student student){
        String sql = "INSERT INTO student(name, gender,mark) " +
                "VALUES (?,?,?)";
        // mảng đối số thay thế vào chỗ ?
        int gen = 0;
        if (!student.isGender()){
            gen = 1;
        }
        String[] args = {student.getName(), Integer.toString(gen),
                Double.toString(student.getMark())};

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
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
            boolean gender = cursor.getString(2).equals("0");
            float mark = cursor.getFloat(3);
            Student st = new Student(id,name,gender,mark);
            list.add(st);
        }
        return list;
    }


    // UPDATE Student SET name = ?, gender = ?,mark=? WHERE id = ?
    public int updateStudent(Student student) {
        int gen = 0;
        if (!student.isGender()){
            gen = 1;
        }
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("gender", gen);
        values.put("mark",student.getMark());
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(student.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("student",
                values, whereClause, whereArgs);
    }

    // DELETE FROM Student WHERE id = ?
    public int deleteStudent(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("student",
                whereClause, whereArgs);
    }

    // GET BY ID
    public Student getStudent(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("student", null,
                whereClause, whereArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int studentId = cursor.getInt(cursor.getColumnIndex("id"));
            String studentName = cursor.getString(cursor.getColumnIndex("name"));
            boolean studentGender = cursor.getString(cursor.getColumnIndex("gender")).equals("0");
            float studentMark=cursor.getFloat(cursor.getColumnIndex("mark"));
            cursor.close();
            return new Student(studentId, studentName, studentGender,studentMark);
        }
        return null;
    }

    // Search by name
    public List<Student> getStudentByName(String name) {
        List<Student> list=new ArrayList<>();
        String whereClause = "name LIKE ?";
        String[] whereArgs = {"%" + name + "%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("student", null, whereClause, whereArgs, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int studentId = cursor.getInt(cursor.getColumnIndex("id"));
            String studentName = cursor.getString(cursor.getColumnIndex("name"));
            boolean studentGender = cursor.getInt(cursor.getColumnIndex("gender"))==1;
            float studentMark=cursor.getFloat(cursor.getColumnIndex("mark"));
            list.add(new Student(studentId, studentName, studentGender,studentMark));
        }
        cursor.close();
        return list;
    }
}
