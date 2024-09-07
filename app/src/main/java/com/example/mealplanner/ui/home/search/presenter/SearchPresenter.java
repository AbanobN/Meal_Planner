package com.example.mealplanner.ui.home.search.presenter;

import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;

public interface SearchPresenter {
    void getAllCategories();

    void getAllCountries();

    void getAllIngredients();

    void searchMeals(String query);

    void onAreaClick(String area);

    void onCategoryClick(String category);

    void onIngredientClick(String ingredient);

    void loadAllMeals();

    void addToFavorite(MealData mealdate);

    void removeFromFavorite(MealEntity meal);

    void clearDisposables();
}
