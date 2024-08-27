package com.example.mealplanner.ui.home.search.view;

import android.content.ComponentCallbacks;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistryOwner;

import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;

import java.util.ArrayList;
import java.util.List;

public interface SearchView extends ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner, HasDefaultViewModelProviderFactory, SavedStateRegistryOwner, ActivityResultCaller, CategoryAdapter.OnCategoryClickListener, IngredientAdapter.OnIngredientClickListener, AreaAdapter.OnAreaClickListener, MealAdapter.OnMealClickListener {

    void updateMealsList(ArrayList<MealData> meals);

    void updateCategoryList(List<CategorieData> categories);

    void updateCountryList(List<AreaData> areas);

    void updateIngredientsList(List<IngredientData> ingredientData);

    void onDestroy();

    @Override
    void onAreaClick(AreaData areaData);

    @Override
    void onCategoryClick(CategorieData categorieData);

    @Override
    void onIngredientClick(IngredientData ingredientData);

    @Override
    void onMealClick(MealData mealData);

    @Override
    void deleteFromFav(MealData meal);

    @Override
    void insertIntoFav(MealData meal);

    void handleError(Throwable t);

    void updateFavoriteList(List<MealEntity> mealEntities);

    void showToast(String msg);
}
