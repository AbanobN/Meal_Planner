package com.example.mealplanner.ui.home.favorites.presenter;

import com.example.mealplanner.data.model.MealEntity;

public interface FavoritesPresenter {
    void loadAllMeals();
//    public void addMeal(MealEntity mealEntity);
    void removeMeal(MealEntity mealEntity);
    public void onDestroy();
}
