package com.example.mealplanner.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PlanMeals")
public class PlanEntity extends BaseEntity {

    @NonNull
    @PrimaryKey
    String primaryKey = "";

    private String weekDay;

    public PlanEntity() {
        // Default constructor
    }

    public PlanEntity(String id, @NonNull String mealName, String mealUrlImg, @NonNull String userEmail, @NonNull String weekDay) {
        super(id, mealName, mealUrlImg, userEmail);
        this.weekDay = weekDay;

        String truncatedMealName = mealName.length() > 5 ? mealName.substring(0, 5) : mealName;
        String truncatedEmail = userEmail.length() > 3 ? userEmail.substring(0, 3) : userEmail;
        String truncatedWeekDay = weekDay.substring(0, 3);

        primaryKey  = truncatedMealName + "_" + truncatedEmail + "_" + truncatedWeekDay;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    @NonNull
    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(@NonNull String primaryKey) {
        this.primaryKey = primaryKey;
    }

}