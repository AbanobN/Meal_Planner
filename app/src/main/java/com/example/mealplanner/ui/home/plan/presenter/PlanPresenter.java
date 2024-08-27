package com.example.mealplanner.ui.home.plan.presenter;

import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

public interface PlanPresenter {
    void insertPlans(List<PlanEntity> plans);

    void loadMealsForDay(String weekday);

    void deletePlan(PlanEntity planEntity);

    void deletePlanFirebase(PlanEntity planEntity);

    void dispose();
}
