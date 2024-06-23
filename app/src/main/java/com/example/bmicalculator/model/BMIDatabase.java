package com.example.bmicalculator.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BMIData.class}, version = 2, exportSchema = false)
public abstract class BMIDatabase extends RoomDatabase {
    public abstract BMIDao bmiDao();

    private static volatile BMIDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BMIDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BMIDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    BMIDatabase.class, "bmi_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static ExecutorService getDatabaseWriteExecutor() {
        return databaseWriteExecutor;
    }
}
