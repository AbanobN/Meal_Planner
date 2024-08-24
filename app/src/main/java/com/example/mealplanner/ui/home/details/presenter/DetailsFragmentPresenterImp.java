package com.example.mealplanner.ui.home.details.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.details.view.DetailsFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsFragmentPresenterImp implements DetailsFragmentPresenter{
    private DetailsFragment view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String user;

    public DetailsFragmentPresenterImp(Context context, DetailsFragment view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
        user = repo.getUserEmail();
    }

    @Override
    public void getMealById(String id)
    {
        compositeDisposable.add(
                repo.getMealById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                mealEntities -> view.updateDetails(mealEntities),
                                throwable -> view.showError(throwable)
                        )
        );
    }

    public void addToFavorite(MealEntity meal)
    {
        meal.setUserEmail(user);

        compositeDisposable.add(
                repo.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d("Favorite", "addToFavorite: "),
                        throwable -> view.showError(throwable)
                        )
        );
    }

    public void addToplan(MealEntity meal ,String weekDay)
    {
        PlanEntity plan = new PlanEntity(meal.getId(),meal.getMealName(),meal.getMealUrlImg(),user,weekDay);

        compositeDisposable.add(
                repo.insertPlan(plan)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d("Favorite", "addToFavorite: "),
                                throwable -> view.showError(throwable)
                        )
        );
    }


    public void onDestroy() {
        compositeDisposable.clear();

    }


}
