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

        String truncatedMealName = mealName.length() > 5 ? mealName.substring(0, 5) : mealName;
        String truncatedEmail = userEmail.length() > 3 ? userEmail.substring(0, 3) : userEmail;

        this.primaryKey = truncatedMealName + "_" + truncatedEmail;
    }
    @NonNull
    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(@NonNull String primaryKey) {
        this.primaryKey = primaryKey;
    }
}