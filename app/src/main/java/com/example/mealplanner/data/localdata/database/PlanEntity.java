package com.example.mealplanner.data.localdata.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlanMeals")
public class PlanEntity {
    @NonNull
    @PrimaryKey
    private String id;
    private String mealName;
    private String mealUrlImg;
    private String userEmail;
    private String weekDay;

    public PlanEntity(String id, String mealName, String mealUrlImg, String userEmail, String weekDay) {
        this.id = id;
        this.mealName = mealName;
        this.mealUrlImg = mealUrlImg;
        this.userEmail = userEmail;
        this.weekDay = weekDay;
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

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}

