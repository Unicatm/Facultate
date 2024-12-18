package com.example.recap4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BurseDAO {

    @Insert
    void addBursa(Bursa bursa);

    @Delete
    void deleteBursa(Bursa bursa);

    @Update
    void updateBursa(Bursa bursa);

    @Query("SELECT * FROM burse")
    List<Bursa> getAllBurse();

    @Query("DELETE FROM burse WHERE tip=:tip")
    void stergeByTip(String tip);

}
