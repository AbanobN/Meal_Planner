package com.example.mealplanner.ui.home.home.presenter;

import android.content.Context;

import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.remotedata.retrofit.CategoryCallback;
import com.example.mealplanner.data.remotedata.retrofit.MealCallback;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.ui.home.home.view.HomeFragment;

import java.util.List;

public class HomeFragmentPresenterImp implements CategoryCallback , MealCallback ,HomeFragmentPresenter {
    private HomeFragment view;
    private AppRepo repo;

    public HomeFragmentPresenterImp(Context context, HomeFragment view) {
        this.view = view;
        this.repo =  AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(context), RetrofitClient.getClient());
    }

    @Override
    public void getCategories()
    {

        repo.getAllCategories(this);
    }

    @Override
    public void getMealsByCategories(String cat)
    {
        repo.getMealsByCategory(cat,this);
    }

    @Override
    public void onCategorySuccess(List<CategorieData> categories) {
        view.handleCategories(categories);
    }

    @Override
    public void onCategoryFailure(Throwable t) {
        view.handError(t);
    }

    @Override
    public void onMealSuccess(List<MealData> meals) {

        view.handleMealsByCategory(meals);
    }

    @Override
    public void onMealFailure(Throwable t) {
        view.handError(t);
    }
}
