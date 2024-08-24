package com.example.mealplanner.ui.home.favorites.presenter;

import android.content.Context;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.favorites.view.FavoritesView;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class FavoritesPresenterImpl implements FavoritesPresenter {

    private final FavoritesView favoritesView;
    private final AppRepo appRepo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavoritesPresenterImpl(FavoritesView favoritesView, Context context) {
        this.favoritesView = favoritesView;
        this.appRepo = (AppRepo) RepositoryProvider.provideRepository(context);
    }

    @Override
    public void loadAllMeals() {
        compositeDisposable.add(
                appRepo.getAllMeals()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                mealEntities -> favoritesView.showMeals(mealEntities),
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void addMeal(MealEntity mealEntity) {
        compositeDisposable.add(
                appRepo.insertMeal(mealEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> favoritesView.showMealAdded(),
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void removeMeal(MealEntity mealEntity) {
        compositeDisposable.add(
                appRepo.deleteMeal(mealEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> favoritesView.showMealRemoved(),
                                throwable -> favoritesView.showError(throwable.getMessage())
                        )
        );
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }
}
