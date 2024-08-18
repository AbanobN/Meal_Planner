package com.example.mealplanner.data.remotedata.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    @GET("random.php")
    Call<ApiResponse.MealResponse> getRandomMeal();

    @GET("search.php")
    Call<ApiResponse.MealResponse> searchMealByName(@Query("s") String mealName);

    @GET("lookup.php")
    Call<ApiResponse.MealResponse> getMealById(@Query("i") String mealId);

    @GET("categories.php")
    Call<ApiResponse.CategoryResponse> getCategories();

    @GET("list.php")
    Call<ApiResponse.ListResponse> getCategoryList(@Query("c") String listType);

    @GET("list.php")
    Call<ApiResponse.ListResponse> getAreaList(@Query("a") String listType);

    @GET("list.php")
    Call<ApiResponse.ListResponse> getIngredientList(@Query("i") String listType);

    @GET("filter.php")
    Call<ApiResponse.MealResponse> filterMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<ApiResponse.MealResponse> filterMealsByArea(@Query("a") String area);
}
