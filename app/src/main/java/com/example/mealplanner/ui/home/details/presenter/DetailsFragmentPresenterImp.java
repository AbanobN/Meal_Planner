package com.example.mealplanner.ui.home.details.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.details.view.DetailsFragment;

import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsFragmentPresenterImp implements DetailsFragmentPresenter {
    private DetailsFragment view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String user;

    public String getUser() {
        return user;
    }

    public DetailsFragmentPresenterImp(AppRepo repo, DetailsFragment view) {
        this.view = view;
        this.repo = repo;
        user = repo.getUserEmail();
    }

    @Override
    public void getMealById(String id) {
        compositeDisposable.add(
                repo.getMealById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(response -> {
                            List<MealData> meals = response.getMeals();
                            if (meals != null && !meals.isEmpty()) {
                                return meals.get(0);
                            } else {
                                throw new NoSuchElementException("No meal found with ID: " + id);
                            }
                        })
                        .onErrorResumeNext(Single::error)
                        .subscribe(
                                mealEntities -> view.updateDetails(mealEntities),
                                throwable -> view.showError(throwable)
                        )
        );
    }

    @Override
    public void addToFavorite(MealEntity meal) {
        meal.setUserEmail(user);
        compositeDisposable.add(
                repo.insertMeal(meal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Add To Favorites"),
                                throwable -> view.showError(throwable)
                        )
        );
    }

    @Override
    public void addToplan(MealEntity meal, String weekDay) {
        PlanEntity plan = new PlanEntity(meal.getId(), meal.getMealName(), meal.getMealUrlImg(), user, weekDay);

        compositeDisposable.add(
                repo.insertPlan(plan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Add To Plan"),
                                throwable -> view.showError(throwable)
                        )
        );
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();

    }


}
