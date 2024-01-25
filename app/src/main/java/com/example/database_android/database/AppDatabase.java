package com.example.database_android.database;

import android.content.Context;

import androidx.room.Room;

import com.example.database_android.Constants;
import com.example.database_android.database.TaskDatabase;

public class AppDatabase {
    private static TaskDatabase instance;

    public static synchronized TaskDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, Constants.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
