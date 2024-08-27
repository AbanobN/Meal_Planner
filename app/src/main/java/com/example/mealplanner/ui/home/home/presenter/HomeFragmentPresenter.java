package com.example.mealplanner.ui.home.home.presenter;

import com.example.mealplanner.data.model.MealEntity;

public interface HomeFragmentPresenter {
    void getCategories();

    void getMealsByCategories(String cat);

    void getRandomMeal();

    void loadAllMeals();

    void addToFavorite(MealEntity meal);

    void removeFromFavorite(MealEntity meal);
}
