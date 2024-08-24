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

public class RetrofitManager {

    private final MealApiService apiService;

    public RetrofitManager() {
        apiService = RetrofitClient.getApiService();
    }

    // All Categories
    public Single<List<CategorieData>> fetchCategories() {
        return apiService.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getCategories())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // All Ingredients
    public Single<List<IngredientData>> fetchAllIngredients() {
        return apiService.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getIngredients())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // All Areas
    public Single<List<AreaData>> fetchAreasList() {
        return apiService.getAreasList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getAreas())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meals By Category
    public Single<List<MealData>> fetchMealsByCategory(String category) {
        return apiService.filterMealsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meals By Ingredient
    public Single<List<MealData>> fetchMealsByIngredient(String ingredient) {
        return apiService.filterMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Meals By Area
    public Single<List<MealData>> fetchMealsByArea(String area) {
        return apiService.filterMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // List of Areas
    public Single<List<AreaData>> fetchAreasList(String listType) {
        return apiService.getAreaList(listType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getAreas())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

    // Random Meal
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
    public Single<List<MealData>> searchMealByName(String mealName) {
        return apiService.searchMealByName(mealName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.getMeals())
                .onErrorResumeNext(throwable -> Single.error(throwable));
    }

}

