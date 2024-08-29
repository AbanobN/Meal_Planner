package com.example.mealplanner.data.remotedata.retrofit;

import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface RetrofitManager {
    // All Categories
    public Single<ApiResponse.CategoryResponse> fetchCategories();

    // All Ingredients
    public Single<ApiResponse.IngredientResponse> fetchAllIngredients();

    // All Areas
    public Single<ApiResponse.AreaResponse>  fetchAllAreas();

    // Meals By Category
    Single<ApiResponse.MealResponse> fetchMealsByCategory(String category);

    // Meals By Area
    public Single<ApiResponse.MealResponse> fetchMealsByAreas(String area);

    // Meals By Ingredient
    Single<ApiResponse.MealResponse> fetchMealsByIngredient(String ingredient);

    // Random Meal
    Single<ApiResponse.MealResponse> fetchRandomMeal();

    // Meal By ID
    Single<ApiResponse.MealResponse> fetchMealById(String mealId);

    // Search Meal By Name
    Single<ApiResponse.MealResponse>searchMealByName(String mealName);
}
