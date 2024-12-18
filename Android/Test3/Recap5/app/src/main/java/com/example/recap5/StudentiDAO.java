package com.example.recap5;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentiDAO {
    @Insert
    void addStud(Student student);
    @Delete
    void deleteStud(Student student);
    @Update
    void updateStud(Student student);
    @Query("SELECT * FROM studenti")
    List<Student> getAllStud();
    @Query("SELECT AVG(medie) FROM studenti WHERE specializare=:spec")
    float afisareMedie(String spec);

    @Query("SELECT nume FROM studenti WHERE specializare=:spec")
    List<String> getNamesBySpec(String spec);
}
