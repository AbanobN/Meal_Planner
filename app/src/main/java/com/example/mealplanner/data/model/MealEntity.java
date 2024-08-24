package com.example.mealplanner.data.model;

import androidx.room.Entity;

@Entity(tableName = "FavoritesMeals")
public class MealEntity extends BaseEntity {

    public MealEntity(String id, String mealName, String mealUrlImg, String userEmail) {
        super(id, mealName, mealUrlImg, userEmail);
    }
}