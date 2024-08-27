package com.example.mealplanner.ui.home.home.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.home.view.HomeFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragmentPresenterImp implements HomeFragmentPresenter {
    private HomeFragment view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String userEmail;

    public HomeFragmentPresenterImp(Context context, HomeFragment view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
        userEmail = repo.getUserEmail();
    }

    @Override
    public void getCategories(){
    compositeDisposable.add(
            repo.getAllCategories() // This internally calls fetchCategories
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
                        .subscribe(
                                meals -> view.handleMealsByCategory(meals),
                                throwable -> view.handError(throwable)
                        )
        );
    }

    @Override
    public void getRandomMeal() {
        compositeDisposable.add(
                repo.getRandomMeal()
                        .subscribe(
                                meal -> view.handleRandomCard(meal),
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
    public void addToFavorite(MealEntity meal)
    {
        meal.setUserEmail(userEmail);

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
