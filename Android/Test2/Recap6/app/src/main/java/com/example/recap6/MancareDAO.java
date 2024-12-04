package com.example.recap6;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MancareDAO {

    @Insert
    void addMancare(Mancare mancare);

    @Query("SELECT * FROM mancare")
    List<Mancare> getAllMancare();
}
