package com.collegegraduate.notesapp;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface MainDAO {

    @Insert
    void insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY pinned DESC, ID DESC")
    List<Notes> getAll();

    @Query("UPDATE notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);

    @Delete
    void delete(Notes notes);

    @Update
    void update(Notes notes);

    @Query("SELECT * FROM notes WHERE title LIKE :query OR notes LIKE :query")
    List<Notes> searchNotes(String query);

    @Query("SELECT * FROM notes WHERE color = :color")
    List<Notes> getByColor(String color);

    @Query("UPDATE notes SET color = :color WHERE ID = :id")
    void updateColor(int id, String color);
}