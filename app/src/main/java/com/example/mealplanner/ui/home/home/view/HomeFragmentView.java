package com.example.mealplanner.ui.home.home.view;

import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;

import java.util.List;

public interface HomeFragmentView {
    public void handleCategories(List<CategorieData> categories);
    public void handleMealsByCategory(List<MealData> meals);
    public void handError(Throwable t);
}
