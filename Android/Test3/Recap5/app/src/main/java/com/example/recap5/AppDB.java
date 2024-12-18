package com.example.recap5;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={Student.class},version = 1,exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    private static String dbName = "app.db";
    private static AppDB instance;

    public static AppDB getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,AppDB.class,dbName).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public abstract StudentiDAO getStudentiDAO();
}
