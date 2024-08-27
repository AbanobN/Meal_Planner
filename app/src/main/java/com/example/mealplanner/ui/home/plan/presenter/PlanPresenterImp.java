package com.example.mealplanner.ui.home.plan.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.plan.view.PlanFragment;
import com.example.mealplanner.ui.home.plan.view.PlanFragmentView;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImp implements PlanPresenter {

    private final PlanFragment view;
    private final AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final String userEmail;


    public PlanPresenterImp(PlanFragment view, Context context) {
        this.view = view;
        this.repo =  (AppRepo) RepositoryProvider.provideRepository(context);
        userEmail = repo.getUserEmail();
    }


    @Override
    public void insertPlans(List<PlanEntity> plans) {
        compositeDisposable.add(
                repo.insertAllPlans(plans)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast("Add To Plan"),
                                throwable -> view.showError("Failed to delete plan: " + throwable.getMessage())
                        )
        );

    }



    @Override
    public void loadMealsForDay(String weekday) {
        repo.getMealsForDay(weekday,userEmail).observe(view.getViewLifecycleOwner(), meals -> {
            if (meals != null) {
                view.showMealsForSelectedDay(meals);
            } else {
                view.showMealsForSelectedDay(new ArrayList<>());
            }
        });
    }

    @Override
    public void deletePlan(PlanEntity planEntity) {
        compositeDisposable.add(
                repo.deletePlan(planEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> view.showToast(planEntity.getWeekDay() +" Deleted From Plan"),
                                throwable -> view.showError("Failed to delete plan: " + throwable.getMessage())
                        )
        );
    }

    @Override
    public void deletePlanFirebase(PlanEntity planEntity) {
        compositeDisposable.add(
                repo.deleteFromFirebase(planEntity.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Log.d("Delete", "Plan successfully deleted.");
                        }, throwable -> {
                            Log.e("Delete", "Error deleting plan: " + throwable.getMessage());
                        })
        );
    }



    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}

