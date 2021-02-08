package com.example.roomdatabase_migrations.Data;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.roomdatabase_migrations.Model.User;

import java.util.List;

public interface UserDAO {
    @Query("SELECT * FROM users ORDER BY id DESC") // notes là tên table được viết trong class entities Note
    List<User> getAllUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);
}
