package com.example.mealplanner.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoritesMeals")
public class MealEntity extends BaseEntity {

    @NonNull
    @PrimaryKey
    String primaryKey = "";

    public MealEntity(){

    }

    public MealEntity(String id, @NonNull String mealName, String mealUrlImg, @NonNull String userEmail) {
        super(id, mealName, mealUrlImg, userEmail);
        Log.d("MealEntity", "MealName: " + mealName);
        Log.d("MealEntity", "UserEmail: " + userEmail);

        String truncatedEmail = (userEmail != null && !userEmail.isEmpty() && userEmail.length() > 3)
                ? userEmail.substring(0, 3)
                : "defaultEmail";

        this.primaryKey = mealName + "_" + truncatedEmail;
        Log.d("MealEntity", "primaryKey: " + primaryKey);
    }
    @NonNull
    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(@NonNull String primaryKey) {
        this.primaryKey = primaryKey;
    }
}