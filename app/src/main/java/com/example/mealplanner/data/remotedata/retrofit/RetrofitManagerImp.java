package com.example.mealplanner.data.remotedata.retrofit;

import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import java.util.List;
import java.util.NoSuchElementException;

public class RetrofitManagerImp implements RetrofitManager {

    private final MealApiService apiService;

    public RetrofitManagerImp() {
        apiService = RetrofitClient.getApiService();
    }

    // All Categories
    @Override
    public Single<ApiResponse.CategoryResponse> fetchCategories() {
        return apiService.getCategories();
    }

    // All Ingredients
    @Override
    public Single<ApiResponse.IngredientResponse> fetchAllIngredients() {
        return apiService.getAllIngredients();
    }

    // All Areas
    @Override
    public Single<ApiResponse.AreaResponse> fetchAllAreas() {
        return apiService.getAreasList();
    }

    // Meals By Category
    @Override
    public Single<ApiResponse.MealResponse> fetchMealsByCategory(String category) {
        return apiService.filterMealsByCategory(category);
    }

    // Meals By Area
    @Override
    public Single<ApiResponse.MealResponse> fetchMealsByAreas(String area)
    {
        return apiService.filterMealsByArea(area);
    }

    // Meals By Ingredient
    @Override
    public  Single<ApiResponse.MealResponse> fetchMealsByIngredient(String ingredient) {
        return apiService.filterMealsByIngredient(ingredient);
    }

    // Random Meal
    @Override
    public Single<ApiResponse.MealResponse> fetchRandomMeal() {
        return apiService.getRandomMeal();
    }

    // Meal By ID
    @Override
    public Single<ApiResponse.MealResponse> fetchMealById(String mealId) {
        return apiService.getMealById(mealId);
    }

    // Search Meal By Name
    @Override
    public  Single<ApiResponse.MealResponse> searchMealByName(String mealName) {
        return apiService.searchMealByName(mealName);
    }

}

