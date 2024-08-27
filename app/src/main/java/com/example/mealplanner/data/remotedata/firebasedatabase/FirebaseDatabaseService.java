package com.example.mealplanner.data.remotedata.firebasedatabase;

import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface FirebaseDatabaseService {
    Completable addPlanEntity(PlanEntity planEntity);

    Completable deletePlanEntity(String mealId);

    Completable addPlanEntityList(List<PlanEntity> planEntities);

    Single<List<PlanEntity>> getAllPlanEntities();

    Observable<List<PlanEntity>> observeAllPlanEntities();

    Single<List<PlanEntity>> getAllPlanEntitiesByUserEmail(String userEmail);
}
