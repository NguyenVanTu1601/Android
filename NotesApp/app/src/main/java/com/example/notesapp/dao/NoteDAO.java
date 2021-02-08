package com.example.notesapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notesapp.entities.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * FROM notes ORDER BY id DESC") // notes là tên table được viết trong class entities Note
    List<Note> gettAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);


}
