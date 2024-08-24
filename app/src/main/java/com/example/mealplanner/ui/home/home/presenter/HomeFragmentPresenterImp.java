package com.example.mealplanner.ui.home.home.presenter;

import android.content.Context;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.home.view.HomeFragment;

public class HomeFragmentPresenterImp implements HomeFragmentPresenter {
    private HomeFragment view;
    private AppRepo repo;

    public HomeFragmentPresenterImp(Context context, HomeFragment view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
    }

    @Override
    public void getCategories()
    {

        repo.getAllCategories();
    }

    @Override
    public void getMealsByCategories(String cat)
    {
        repo.getMealsByCategory(cat);
    }

//    @Override
//    public void onCategorySuccess(List<CategorieData> categories) {
//        view.handleCategories(categories);
//    }
//
//    @Override
//    public void onCategoryFailure(Throwable t) {
//        view.handError(t);
//    }
//
//    @Override
//    public void onMealSuccess(List<MealData> meals) {
//
//        view.handleMealsByCategory(meals);
//    }
//
//    @Override
//    public void onMealFailure(Throwable t) {
//        view.handError(t);
//    }
}
