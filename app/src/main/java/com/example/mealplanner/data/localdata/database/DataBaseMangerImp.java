package com.example.mealplanner.data.localdata.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class DataBaseMangerImp implements DataBaseManger {

    private final MealDAO mealDAO;
    private final PlanDAO planDAO ;

    public DataBaseMangerImp(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        mealDAO = db.mealDAO();
        planDAO = db.planDAO();
    }

    @Override
    public Flowable<List<MealEntity>> getAllFavoritesByUserEmail(String userEmail) {
        return mealDAO.getAllByUserEmail(userEmail);
    }

    @Override
    public Completable insertMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.insert(mealEntity));
    }

    @Override
    public Completable deleteMeal(MealEntity mealEntity) {
        return Completable.fromAction(() -> mealDAO.delete(mealEntity));
    }


    @Override
    public Completable insertPlan(PlanEntity planEntity) {
        return Completable.fromAction(() -> planDAO.insertPlan(planEntity));
    }

    @Override
    public Completable deletePlan(PlanEntity planEntity) {
        return Completable.fromAction(() -> planDAO.deletePlan(planEntity));
    }

    @Override
    public Single<List<PlanEntity>> getPlansByUserEmail(String userEmail) {
        return Single.fromCallable(() -> planDAO.getPlansByUserEmail(userEmail));
    }

    @Override
    public Completable insertAllPlans(List<PlanEntity> planEntities) {
        return Completable.fromAction(() -> planDAO.insertPlans(planEntities));
    }

    @Override
    public LiveData<List<PlanEntity>> getMealsForDay(String weekDay, String userEmail) {
        return planDAO.getPlansByDayAndUser(weekDay,userEmail);
    }
    

}
