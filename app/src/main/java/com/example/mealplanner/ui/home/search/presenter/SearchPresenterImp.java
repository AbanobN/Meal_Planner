package com.example.mealplanner.ui.home.search.presenter;

import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.ui.home.search.view.SearchFragment;
import java.util.ArrayList;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {
    private final SearchFragment view;
    private final AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String userEmail;

    public SearchPresenterImp(AppRepo repo, SearchFragment view) {
        this.view = view;
        this.repo = repo;
        userEmail = repo.getUserEmail();
    }

    @Override
    public void getAllCategories()
    {
        compositeDisposable.add(
                repo.getAllCategories()
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
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.MealResponse::getMeals) // Extract the list of meals from the response
                        .onErrorResumeNext(Single::error)
                        .map(ArrayList::new)
                        .subscribe(
                                meals -> view.updateMealsList(new ArrayList<>(meals)),
                                view::handleError
                        )
        );
    }

    @Override
    public void onCategoryClick(String category)
    {
        compositeDisposable.add(
                repo.getMealsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.MealResponse::getMeals)
                        .onErrorResumeNext(Single::error)
                        .map(ArrayList::new)
                        .map(ArrayList::new)
                        .subscribe(
                                view::updateMealsList,
                                view::handleError
                        )
        );
    }

    @Override
    public void onAreaClick(String area)
    {
        compositeDisposable.add(
                repo.getMealsByArea(area)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.MealResponse::getMeals)
                        .onErrorResumeNext(Single::error)
                        .map(ArrayList::new)
                        .subscribe(
                                view::updateMealsList,
                                view::handleError
                        )
        );
    }

    @Override
    public void onIngredientClick(String ingredient)
    {
        compositeDisposable.add(
                repo.getMealsByIngredient(ingredient)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(ApiResponse.MealResponse::getMeals) // Extract the meals list from the response
                        .onErrorResumeNext(Single::error)
                        .map(ArrayList::new)
                        .subscribe(
                                view::updateMealsList,
                                view::handleError
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
                                view::updateFavoriteList,
                                view::handleError
                        )
        );
    }

    @Override
    public void addToFavorite(MealData mealdata)
    {
        MealEntity meal = new MealEntity(mealdata.getIdMeal() , mealdata.getStrMeal(),mealdata.getStrMealThumb() , userEmail);

        compositeDisposable.add(
                repo.insertMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Add To Favorite"),
                                view::handleError
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
                                view::handleError
                        )
        );
    }

    @Override
    public void clearDisposables() {
        compositeDisposable.clear();
    }
}
