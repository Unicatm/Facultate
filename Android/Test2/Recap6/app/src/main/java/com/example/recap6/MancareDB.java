package com.example.recap6;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({Converters.class})
@Database(entities = {Mancare.class},version = 1,exportSchema = false)
public abstract class MancareDB extends RoomDatabase {
    private static String dbName = "mancare.db";
    private static MancareDB instance;

    public static MancareDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,MancareDB.class,dbName).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract MancareDAO getMancareDAO();
}
