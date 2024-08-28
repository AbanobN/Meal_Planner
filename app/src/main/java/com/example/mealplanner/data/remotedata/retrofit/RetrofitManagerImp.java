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
    public Single<ApiResponse.AreaResponse> fetchAreasList() {
        return apiService.getAreasList();
    }

    // complite from here

    // List of Areas
    @Override
    public Single<List<AreaData>> fetchMealsByAreas(String Area) {
        return apiService.getAreaList(Area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getAreas())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meals By Category
    @Override
    public Single<List<MealData>> fetchMealsByCategory(String category) {
        return apiService.filterMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meals By Ingredient
    @Override
    public Single<List<MealData>> fetchMealsByIngredient(String ingredient) {
        return apiService.filterMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meals By Area
    @Override
    public Single<List<MealData>> fetchMealsByArea(String area) {
        return apiService.filterMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }


    // Random Meal
    @Override
    public Single<MealData> fetchRandomMeal() {
        return apiService.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    List<MealData> meals = response.getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        return meals.get(0);
                    } else {
                        throw new NoSuchElementException("No random meal found");
                    }
                })
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meal By ID
    @Override
    public Single<MealData> fetchMealById(String mealId) {
        return apiService.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    List<MealData> meals = response.getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        return meals.get(0);
                    } else {
                        throw new NoSuchElementException("No meal found with ID: " + mealId);
                    }
                })
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Search Meal By Name
    @Override
    public Single<List<MealData>> searchMealByName(String mealName) {
        return apiService.searchMealByName(mealName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

}

