package com.example.mealplanner.ui.home.home.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.home.view.HomeFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragmentPresenterImp implements HomeFragmentPresenter {
    private HomeFragment view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String userEmail;

    public HomeFragmentPresenterImp(AppRepo repo, HomeFragment view) {
        this.view = view;
        this.repo = repo;
        userEmail = repo.getUserEmail();
    }

    @Override
    public void getRandomMeal() {
        compositeDisposable.add(
                repo.getRandomMeal()
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
                        .onErrorResumeNext(Single::error)
                        .subscribe(
                                meal -> view.handleRandomCard(meal),
                                throwable -> view.handError(throwable)
                        )
        );
    }

    @Override
    public void getCategories(){
    compositeDisposable.add(
            repo.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(ApiResponse.CategoryResponse::getCategories)
            .onErrorResumeNext(Single::error)
            .subscribe(
            categories -> view.handleCategories(categories),
            throwable -> view.handError(throwable)
            )
        );
    }

    @Override
    public void getMealsByCategories(String cat)
    {
        compositeDisposable.add(
                repo.getMealsByCategory(cat)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.MealResponse::getMeals) // Extract the meals list from the response
                        .onErrorResumeNext(Single::error)
                        .map(ArrayList::new)
                        .subscribe(
                                meals -> view.handleMealsByCategory(meals),
                                throwable -> view.handError(throwable)
                        )
        );
    }

    @Override
    public void loadAllMeals() {
        compositeDisposable.add(
                repo.getAllMeals(userEmail)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                mealEntities -> view.updateFavoriteList(mealEntities),
                                throwable -> view.handError(throwable)
                        )
        );
    }

    @Override
    public void addToFavorite(MealData mealdata)
    {
        MealEntity meal = new MealEntity(mealdata.getIdMeal(),mealdata.getStrMeal(),mealdata.getStrMealThumb(),userEmail);
        compositeDisposable.add(
                repo.insertMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Add To Favorite"),
                                throwable -> view.handError(throwable)
                        )
        );
    }

    @Override
    public void removeFromFavorite(MealEntity meal) {
        meal.setUserEmail(userEmail);
        compositeDisposable.add(
                repo.deleteMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Remove From Favorite"),
                                throwable -> view.handError(throwable)
                        )
        );
    }

}
