package com.example.mealplanner.data.localdata.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FavoritesMeals")
public class MealEntity {

    @NonNull
    @PrimaryKey
    private String id;

    private String mealName;
    private String mealUrlImg;
    private String userEmail;

    public MealEntity(String id, String mealName, String mealUrlImg, String userEmail) {
        this.id = id;
        this.mealName = mealName;
        this.mealUrlImg = mealUrlImg;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealUrlImg() {
        return mealUrlImg;
    }

    public void setMealUrlImg(String mealUrlImg) {
        this.mealUrlImg = mealUrlImg;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
