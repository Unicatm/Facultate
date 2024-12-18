package com.example.recap4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Bursa.class},version = 1,exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private final static String dbName = "app.db";
    private static AppDB instance;

    public static AppDB getInstance(Context context){

        if(instance==null){
            instance = Room.databaseBuilder(context,AppDB.class,dbName).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }

        return instance;
    }

    public abstract BurseDAO getBurseDAO();
}
