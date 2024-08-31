package com.example.mealplanner.ui.home.favorites.view;

import com.example.mealplanner.data.model.MealEntity;

import java.util.List;

public interface FavoritesView {
    void showMeals(List<MealEntity> mealEntities);
    void showMealRemoved();
    void showError(String error);
}

