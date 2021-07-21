package com.example.tugas5.entity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DataBuku.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataBukuDAO dao();
    private static AppDatabase appDatabase;

    public static  AppDatabase inidb(Context context){
        if (appDatabase==null)
            appDatabase = Room.databaseBuilder(context, AppDatabase.class,"dbKlub").allowMainThreadQueries().build();
        return appDatabase;

    }
}
