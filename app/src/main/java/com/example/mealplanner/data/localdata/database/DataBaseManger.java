package com.example.mealplanner.data.localdata.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class DataBaseManger {

    private final MealDAO mealDAO;
    private final PlanDAO planDAO ;

    public DataBaseManger(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealDAO = db.mealDAO();
        planDAO = db.planDAO();
    }

    public Flowable<List<MealEntity>> getAllFavoritesByUserEmail(String userEmail) {
        return mealDAO.getAllByUserEmail(userEmail);
    }

    public Completable insertMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.insert(mealEntity));
    }

    public Completable deleteMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.delete(mealEntity));
    }


    public Completable insertPlan(PlanEntity planEntity) {
        return Completable.fromAction(() -> planDAO.insertPlan(planEntity));
    }

    public Completable deletePlan(PlanEntity planEntity) {
        return Completable.fromAction(() -> planDAO.deletePlan(planEntity));
    }

    public Single<List<PlanEntity>> getPlansByUserEmail(String userEmail) {
        return Single.fromCallable(() -> planDAO.getPlansByUserEmail(userEmail));
    }

    public Completable insertAllPlans(List<PlanEntity> planEntities) {
        return Completable.fromAction(() -> planDAO.insertPlans(planEntities));
    }

    public LiveData<List<PlanEntity>> getMealsForDay(String weekDay, String userEmail) {
        return planDAO.getPlansByDayAndUser(weekDay,userEmail);
    }



}
