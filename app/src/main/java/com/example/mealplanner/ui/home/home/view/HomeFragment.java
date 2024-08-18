package com.example.mealplanner.ui.home.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.data.remotedata.retrofit.MealApiService;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener {
    TextView type;
    CategoryAdapter categoryAdapter;
    MealAdapter mealAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        RecyclerView recyclerView = view.findViewById(R.id.cat_rec_view);
        RecyclerView recyclerView2 = view.findViewById(R.id.meal_rec_view);
        type = view.findViewById(R.id.type);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);

        categoryAdapter = new CategoryAdapter(new ArrayList<>(),this);
        mealAdapter = new MealAdapter(new ArrayList<>());

        recyclerView.setAdapter(categoryAdapter);
        recyclerView2.setAdapter(mealAdapter);

        fetchCategories();

        fetchMealsByCategory("Beef");
        type.setText("Beef Meals");
    }

    @Override
    public void onCategoryClick(CategorieData categorieData) {
        type.setText(categorieData.getStrCategory() + " Meals");
        fetchMealsByCategory(categorieData.getStrCategory());

    }

    private void fetchCategories() {
        MealApiService apiService = RetrofitClient.getClient().create(MealApiService.class);
        apiService.getCategories().enqueue(new Callback<ApiResponse.CategoryResponse>() {
            @Override
            public void onResponse(Call<ApiResponse.CategoryResponse> call, Response<ApiResponse.CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategorieData> categories = response.body().getCategories();
                    categoryAdapter.updateCategoryDataList(categories);
                } else {
                    Log.e("HomeFragment", "Failed to fetch categories: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse.CategoryResponse> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching categories", t);
            }
        });
    }

    private void fetchMealsByCategory(String category) {
        MealApiService apiService = RetrofitClient.getClient().create(MealApiService.class);

        // Make the API call to get meals by category
        apiService.filterMealsByCategory(category).enqueue(new Callback<ApiResponse.MealResponse>() {
            @Override
            public void onResponse(Call<ApiResponse.MealResponse> call, Response<ApiResponse.MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MealData> meals = response.body().getMeals();
                    // Update the MealAdapter with the fetched meals
                    mealAdapter.updateMealDataList(meals);
                } else {
                    Log.e("HomeFragment", "Failed to fetch meals: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse.MealResponse> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching meals", t);
            }
        });
    }


}