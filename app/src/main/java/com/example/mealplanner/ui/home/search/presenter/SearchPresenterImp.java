package com.example.mealplanner.ui.home.search.presenter;

import android.content.Context;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.search.view.SearchFragment;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {
    private final SearchFragment view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String userEmail;

    public SearchPresenterImp(Context context, SearchFragment view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
        userEmail = repo.getUserEmail();
    }

    @Override
    public void getAllCategories()
    {
        compositeDisposable.add(
                repo.getAllCategories() // This internally calls fetchCategories
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.CategoryResponse::getCategories)
                        .onErrorResumeNext(Single::error)
                        .subscribe(
                                view::updateCategoryList,
                                view::handleError
                        )
        );
    }

    @Override
    public void getAllIngredients()
    {
        compositeDisposable.add(
                repo.getAllIngredients()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.IngredientResponse::getIngredients)
                        .onErrorResumeNext(Single::error)
                        .subscribe(
                                view::updateIngredientsList,
                                view::handleError
                        )
        );
    }

    @Override
    public void getAllCountries()
    {
        compositeDisposable.add(
                repo.getAreasList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.AreaResponse::getAreas)
                        .onErrorResumeNext(Single::error)
                        .subscribe(
                                view::updateCountryList,
                                view::handleError
                        )
        );
    }

    @Override
    public void searchMeals(String query)
    {
        compositeDisposable.add(
                repo.searchMealByName(query)
                        .subscribe(
                                meals -> view.updateMealsList(new ArrayList<>(meals)),
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    @Override
    public void onAreaClick(String area)
    {
        compositeDisposable.add(
                repo.getMealsByArea(area)
                        .map(meals -> new ArrayList<>(meals))
                        .subscribe(
                                arrayListMeals  -> view.updateMealsList(arrayListMeals),
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    @Override
    public void onCategoryClick(String category)
    {
        compositeDisposable.add(
                repo.getMealsByCategory(category)
                        .map(meals -> new ArrayList<>(meals))
                        .subscribe(
                                arrayListMeals  -> view.updateMealsList(arrayListMeals), // Define how to show meals in your view
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    @Override
    public void onIngredientClick(String ingredient)
    {
        compositeDisposable.add(
                repo.getMealsByIngredient(ingredient)
                        .map(meals -> new ArrayList<>(meals))
                        .subscribe(
                                arrayListMeals -> view.updateMealsList(arrayListMeals),
                                throwable -> view.handleError(throwable)
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
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    @Override
    public void addToFavorite(MealEntity meal)
    {
        meal.setUserEmail(userEmail);

        compositeDisposable.add(
                repo.insertMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Add To Favorite"),
                                throwable -> view.handleError(throwable)
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
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    @Override
    public void clearDisposables() {
        compositeDisposable.clear();
    }
}
