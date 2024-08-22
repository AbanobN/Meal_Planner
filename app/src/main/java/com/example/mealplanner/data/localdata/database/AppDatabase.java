package com.example.mealplanner.data.localdata.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MealEntity.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        private static final String DataBase_Name = "UserData";
        public abstract MealDAO mealDAO();
        // Add Plan Table here
        private static AppDatabase instance = null;
        public static synchronized AppDatabase getInstance(Context context){
            if(instance == null)
            {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DataBase_Name).build();
            }
            return instance;
        }
    }
