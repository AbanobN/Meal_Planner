package com.example.mealplanner.ui.home.home.view;

import android.widget.Toast;

import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;

import java.util.List;

public interface HomeFragmentView {
    public void handleCategories(List<CategorieData> categories);
    public void handleMealsByCategory(List<MealData> meals);
    public void handError(Throwable t);
    public void showToast(String msg);
    public void updateFavoriteList(List<MealEntity> favMeals);
    public void handleRandomCard(MealData mealData);
}
