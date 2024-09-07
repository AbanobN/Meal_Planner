package com.example.mealplanner.ui.home.home.presenter;

import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;

public interface HomeFragmentPresenter {
    void getCategories();

    void getMealsByCategories(String cat);

    void getRandomMeal();

    void loadAllMeals();

    void addToFavorite(MealData mealdata);

    void removeFromFavorite(MealEntity meal);
}
