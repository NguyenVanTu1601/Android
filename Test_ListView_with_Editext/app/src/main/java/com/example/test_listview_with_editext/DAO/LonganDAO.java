package com.example.test_listview_with_editext.DAO;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.test_listview_with_editext.Entities.Longan;

import java.util.List;

public interface LonganDAO {

    @Query("SELECT * FROM longan ORDER BY id DESC")
    List<Longan> gettAllLongan();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Longan longan);

    @Delete
    void deleteNote(Longan longan);
}
