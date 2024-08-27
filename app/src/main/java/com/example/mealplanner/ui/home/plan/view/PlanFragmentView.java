package com.example.mealplanner.ui.home.plan.view;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

public interface PlanFragmentView {
    void showDayMealsLabel(String day);
    void hideDayMealsLabel();
    void showError(String message);
    public void showMealsForSelectedDay(List<PlanEntity> meals);
    public void showToast(String msg);
}