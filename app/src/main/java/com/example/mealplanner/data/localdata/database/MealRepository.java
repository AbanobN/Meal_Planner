package com.example.mealplanner.data.localdata.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealRepository {

    private final MealDAO mealDAO;
    private final PlanDAO planDAO ;

    public MealRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealDAO = db.mealDAO();
        planDAO = db.planDAO();
    }

    // Get all favorite meals as Flowable
    public Flowable<List<MealEntity>> getAllMeals() {
        return mealDAO.getAll(); // This returns a Flowable
    }

    // Insert a new meal
    public Completable insertMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.insert(mealEntity));
    }

    // Delete a meal
    public Completable deleteMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.delete(mealEntity));
    }

    public LiveData<List<PlanEntity>> getAllPlans(String weekDay) {
        return planDAO.getMealsForDay(weekDay);
    }

    public Completable insertPlan(PlanEntity planEntity) {
        return Completable.fromAction(() -> planDAO.insertPlan(planEntity));
    }

    public Completable deletePlan(PlanEntity planEntity) {
        return Completable.fromAction(() -> planDAO.deletePlan(planEntity));
    }
}
