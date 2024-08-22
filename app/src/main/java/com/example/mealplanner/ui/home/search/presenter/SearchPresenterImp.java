package com.example.mealplanner.ui.home.search.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.remotedata.retrofit.ApiResponse;
import com.example.mealplanner.data.remotedata.retrofit.MealApiService;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import com.example.mealplanner.ui.home.search.view.SearchView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {
    private final SearchView view;
    private final Context context;
    private final MealApiService apiService;
    private final CompositeDisposable compositeDisposable;

    public SearchPresenterImp(Context context, SearchView view) {
        this.context = context;
        this.view = view;
        this.apiService = RetrofitClient.retrofit.create(MealApiService.class);
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getCategories() {
        apiService.getCategoriesRX()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.CategoryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.CategoryResponse response) {
                        view.updateCategoryList(response.getCategories());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("SearchPresenter", "Error fetching categories", e);
                    }
                });
    }

    @Override
    public void getAllCountries() {
        apiService.getAreasList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.AreaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.AreaResponse response) {
                        view.updateCountryList(response.getAreas());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("SearchPresenter", "Error fetching areas", e);
                    }
                });
    }

    @Override
    public void getAllIngredients() {
        apiService.getAllIngredients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.IngredientResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.IngredientResponse response) {
                        view.updateIngredientsList(response.getIngredients());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("SearchPresenter", "Error fetching ingredients", e);
                    }
                });
    }

    @Override
    public void searchMeals(String query) {
        apiService.searchMealByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // Handle subscription if necessary
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.MealResponse mealResponse) {
                        // Pass data to view
                        if (mealResponse.getMeals() != null) {
                            view.updateMealsList(new ArrayList<>(mealResponse.getMeals()));
                        } else {
                            view.updateMealsList(new ArrayList<>()); // Empty list if no meals found
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Handle error
                        Log.e("SearchPresenter", "Error fetching meals", e);
                    }
                });
    }


    @Override
    public void onAreaClick(String area) {
        apiService.filterMealsByArea(area)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.MealResponse response) {
                        view.updateMealsList(new ArrayList<>(response.getMeals()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("SearchPresenter", "Error fetching meals by area", e);
                    }
                });
    }

    @Override
    public void onCategoryClick(String category) {
        apiService.filterMealsByCategoryRx(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.MealResponse response) {
                        view.updateMealsList(new ArrayList<>(response.getMeals()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("SearchPresenter", "Error fetching meals by category", e);
                    }
                });
    }

    @Override
    public void onIngredientClick(String ingredient) {
        apiService.filterMealsByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse.MealResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull ApiResponse.MealResponse response) {
                        view.updateMealsList(new ArrayList<>(response.getMeals()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("SearchPresenter", "Error fetching meals by ingredient", e);
                    }
                });
    }

    @Override
    public void clearDisposables() {
        compositeDisposable.clear();
    }
}
