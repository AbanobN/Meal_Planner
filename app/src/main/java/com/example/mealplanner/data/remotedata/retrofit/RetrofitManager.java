package com.example.mealplanner.data.remotedata.retrofit;

import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface RetrofitManager {
    // All Categories
    Single<List<CategorieData>> fetchCategories();

    // All Ingredients
    Single<List<IngredientData>> fetchAllIngredients();

    // All Areas
    Single<List<AreaData>> fetchAreasList();

    // List of Areas
    Single<List<AreaData>> fetchMealsByAreas(String Area);

    // Meals By Category
    Single<List<MealData>> fetchMealsByCategory(String category);

    // Meals By Ingredient
    Single<List<MealData>> fetchMealsByIngredient(String ingredient);

    // Meals By Area
    Single<List<MealData>> fetchMealsByArea(String area);

    // Random Meal
    Single<MealData> fetchRandomMeal();

    // Meal By ID
    Single<MealData> fetchMealById(String mealId);

    // Search Meal By Name
    Single<List<MealData>> searchMealByName(String mealName);
}
