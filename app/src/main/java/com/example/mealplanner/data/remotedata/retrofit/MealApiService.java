package com.example.mealplanner.data.remotedata.retrofit;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    //Callback  Queries
    @GET("random.php")
    Call<ApiResponse.MealResponse> getRandomMeal();

    @GET("lookup.php")
    Call<ApiResponse.MealResponse> getMealById(@Query("i") String mealId);

    @GET("categories.php")
    Call<ApiResponse.CategoryResponse> getCategories();

    @GET("list.php")
    Call<ApiResponse.AreaResponse> getAreaList(@Query("a") String listType);

    @GET("filter.php")
    Call<ApiResponse.MealResponse> filterMealsByCategory(@Query("c") String category);


    // RX Queries
    @GET("search.php")
    Single<ApiResponse.MealResponse> searchMealByName(@Query("s") String mealName);

    @GET("categories.php")
    Single<ApiResponse.CategoryResponse> getCategoriesRX();

    @GET("filter.php")
    Single<ApiResponse.MealResponse> filterMealsByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<ApiResponse.MealResponse> filterMealsByCategoryRx(@Query("c") String category);

    @GET("filter.php")
    Single<ApiResponse.MealResponse> filterMealsByArea(@Query("a") String area);

    @GET("list.php?a=list")
    Single<ApiResponse.AreaResponse> getAreasList();

    @GET("list.php?i=list")
    Single<ApiResponse.IngredientResponse> getAllIngredients();
}
