package com.example.mealplanner.data.model;

import androidx.room.Entity;

@Entity(tableName = "PlanMeals")
public class PlanEntity extends BaseEntity {

    private String weekDay;

    public PlanEntity() {
        // Default constructor
    }

    public PlanEntity(String id, String mealName, String mealUrlImg, String userEmail, String weekDay) {
        super(id, mealName, mealUrlImg, userEmail);
        this.weekDay = weekDay;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}