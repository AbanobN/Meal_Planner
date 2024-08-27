package com.example.mealplanner.ui.home.details.presenter;

import com.example.mealplanner.data.model.MealEntity;

public interface DetailsFragmentPresenter {
    public void getMealById(String id);
    public void addToFavorite(MealEntity meal);
    public void addToplan(MealEntity meal ,String weekDay);
    public void onDestroy();
}
