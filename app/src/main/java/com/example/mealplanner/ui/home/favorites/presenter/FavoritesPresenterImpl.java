package com.example.mealplanner.ui.home.favorites.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.favorites.view.FavoritesView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class FavoritesPresenterImpl implements FavoritesPresenter {

    private final FavoritesView favoritesView;
    private final AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String userEmail;

    public FavoritesPresenterImpl(FavoritesView favoritesView, AppRepo repo) {
        this.favoritesView = favoritesView;
        this.repo = repo;
        userEmail = repo.getUserEmail();
    }

    @Override
    public void loadAllMeals() {
        compositeDisposable.add(
                repo.getAllMeals(userEmail)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                favoritesView::showMeals,
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void removeMeal(MealEntity mealEntity) {
        compositeDisposable.add(
                repo.deleteMeal(mealEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                favoritesView::showMealRemoved,
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
