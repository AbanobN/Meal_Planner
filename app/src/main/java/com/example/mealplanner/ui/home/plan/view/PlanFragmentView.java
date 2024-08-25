package com.example.mealplanner.ui.home.plan.view;

import com.example.mealplanner.data.model.MealEntity;

import java.util.List;

public interface PlanFragmentView {
    void showMealsForSelectedDay(List<MealEntity> meals);
    void showDayMealsLabel(String day);
    void hideDayMealsLabel();
    void showError(String message);
}