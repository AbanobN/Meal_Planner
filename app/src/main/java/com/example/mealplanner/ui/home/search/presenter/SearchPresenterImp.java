package com.example.mealplanner.ui.home.search.presenter;

import android.content.Context;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.search.view.SearchView;
import java.util.ArrayList;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchPresenterImp{
    private final SearchView view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SearchPresenterImp(Context context, SearchView view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
    }



    public void getAllCategories()
    {
        compositeDisposable.add(
                repo.getAllCategories() // This internally calls fetchCategories
                        .subscribe(
                                categories -> view.updateCategoryList(categories),
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    public void getAllCountries()
    {
        compositeDisposable.add(
                repo.getAreasList()
                        .subscribe(
                                areas -> view.updateCountryList(areas),
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    public void getAllIngredients()
    {
        compositeDisposable.add(
                repo.getAllIngredients()
                        .subscribe(
                                ingredients -> view.updateIngredientsList(ingredients),
                                throwable -> view.handleError(throwable)
                        )
        );
    }

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

    public void onIngredientClick(String ingredient)
    {
        compositeDisposable.add(
                repo.getMealsByIngredient(ingredient)
                        .map(meals -> new ArrayList<>(meals)) // Convert List to ArrayList
                        .subscribe(
                                arrayListMeals -> view.updateMealsList(arrayListMeals), // Update the view with ArrayList
                                throwable -> view.handleError(throwable)
                        )
        );
    }

    public void clearDisposables() {
        compositeDisposable.clear();
    }
}
