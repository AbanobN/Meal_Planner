package com.example.mealplanner.data.remotedata.retrofit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static RetrofitClient client = null;
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getClient() {
        if (client == null) {
            client = new RetrofitClient();
        }
        return client;
    }

    // All Categories
    public void fetchCategories(CategoryCallback categoryCallback) {
        MealApiService apiService = RetrofitClient.retrofit.create(MealApiService.class);
        apiService.getCategories().enqueue(new Callback<ApiResponse.CategoryResponse>() {
            @Override
            public void onResponse(Call<ApiResponse.CategoryResponse> call, Response<ApiResponse.CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategorieData> categories = response.body().getCategories();
                    categoryCallback.onCategorySuccess(categories);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse.CategoryResponse> call, Throwable t) {
                categoryCallback.onCategoryFailure(t);
            }
        });
    }

    // Meals By Category
    public void fetchMealsByCategory(String category ,MealCallback mealCallback) {
        MealApiService apiService = RetrofitClient.retrofit.create(MealApiService.class);

        apiService.filterMealsByCategory(category).enqueue(new Callback<ApiResponse.MealResponse>() {
            @Override
            public void onResponse(Call<ApiResponse.MealResponse> call, Response<ApiResponse.MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealData> meals = response.body().getMeals();
                    mealCallback.onMealSuccess(meals);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse.MealResponse> call, Throwable t) {
                mealCallback.onMealFailure(t);
                Log.e("HomeFragment", "Error fetching meals", t);
            }
        });
    }

    // Meal by ID
    public void fetchMealById(String mealId, OneMealCallback mealCallback) {
        MealApiService apiService = RetrofitClient.retrofit.create(MealApiService.class);

        apiService.getMealById(mealId).enqueue(new Callback<ApiResponse.MealResponse>() {
            @Override
            public void onResponse(Call<ApiResponse.MealResponse> call, Response<ApiResponse.MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealData> meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        mealCallback.onMealSuccess(meals.get(0));
                    }
                }
            }
            @Override
            public void onFailure(Call<ApiResponse.MealResponse> call, Throwable t) {
                mealCallback.onMealFailure(t);
                Log.e("MealRepository", "Error fetching meal", t);
            }
        });
    }



}
