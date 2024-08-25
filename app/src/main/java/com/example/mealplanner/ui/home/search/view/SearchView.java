package com.example.mealplanner.ui.home.search.view;


import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;

import java.util.ArrayList;
import java.util.List;

public interface SearchView {
    void updateMealsList(ArrayList<MealData> meals);
    void updateCategoryList(List<CategorieData> categories);
    void updateCountryList(List<AreaData> areas);
    void updateIngredientsList(List<IngredientData> ingredientData);
    public void handleError(Throwable t);
}
