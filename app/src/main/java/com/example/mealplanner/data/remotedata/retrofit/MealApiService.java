package com.example.mealplanner.data.remotedata.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    // RX Queries
    @GET("random.php")
    Single<ApiResponse.MealResponse> getRandomMeal();

    @GET("lookup.php")
    Single<ApiResponse.MealResponse> getMealById(@Query("i") String mealId);

    @GET("categories.php")
    Single<ApiResponse.CategoryResponse> getCategories();

    @GET("list.php")
    Single<ApiResponse.AreaResponse> getAreaList(@Query("a") String listType);

    @GET("filter.php")
    Single<ApiResponse.MealResponse> filterMealsByCategory(@Query("c") String category);

    @GET("search.php")
    Single<ApiResponse.MealResponse> searchMealByName(@Query("s") String mealName);

    @GET("filter.php")
    Single<ApiResponse.MealResponse> filterMealsByIngredient(@Query("i") String ingredient);


    @GET("filter.php")
    Single<ApiResponse.MealResponse> filterMealsByArea(@Query("a") String area);

    @GET("list.php?a=list")
    Single<ApiResponse.AreaResponse> getAreasList();

    @GET("list.php?i=list")
    Single<ApiResponse.IngredientResponse> getAllIngredients();
}