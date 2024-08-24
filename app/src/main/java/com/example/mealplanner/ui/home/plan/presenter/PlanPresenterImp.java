//package com.example.mealplanner.ui.home.plan.presenter;
//
//import android.util.Log;
//import com.example.mealplanner.R;
//import com.example.mealplanner.data.localdata.database.AppDatabase;
//import com.example.mealplanner.data.localdata.database.MealDAO;
//import com.example.mealplanner.data.localdata.database.PlanDAO;
//import com.example.mealplanner.data.model.PlanEntity;
//import com.example.mealplanner.data.remotedata.retrofit.MealApiService;
//import com.example.mealplanner.data.repo.AppRepo;
//import com.example.mealplanner.ui.home.plan.view.PlanFragmentView;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.core.Completable;
//import io.reactivex.rxjava3.schedulers.Schedulers;
//
//public class PlanPresenterImp {
//
//    private final PlanFragmentView view;
//    private final AppRepo repo;
//
//    MealApiService mealApiService ;
//    MealDAO mealDAO ;
//    PlanDAO planDAO ;
//
//
//    public PlanPresenterImp(PlanFragmentView view, AppRepo repo) {
//        this.view = view;
//        this.repo = repo;
//         mealApiService = RetrofitClient.getApiService();
//         mealDAO = AppDatabase.getInstance(getApplicationContext()).mealDAO();
//         planDAO = AppDatabase.getInstance(getApplicationContext()).planDAO();
//    }
//
//    public void generateAndSaveRandomPlans() {
//        compositeDisposable.add(
//                mealApiService.getRandomMealRx()
//                        .flatMapCompletable(mealResponse -> {
//                            if (mealResponse != null && mealResponse.getMeals() != null) {
//                                List<PlanEntity> planEntities = new ArrayList<>();
//                                for (Meal meal : mealResponse.getMeals()) {
//                                    PlanEntity planEntity = new PlanEntity();
//                                    planEntity.setMealId(meal.getIdMeal());
//                                    planEntity.setMealName(meal.getStrMeal());
//                                    // Set other PlanEntity fields as needed
//
//                                    planEntities.add(planEntity);
//                                }
//                                return Completable.fromAction(() -> planDAO.insertPlans(planEntities));
//                            } else {
//                                return Completable.complete(); // Handle case where no meals are found
//                            }
//                        })
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                () -> view.onRandomPlansGenerated(),
//                                throwable -> view.onError(throwable.getMessage())
//                        )
//        );
//    }
//
//
//
//    private void insertPlans(List<PlanEntity> plans) {
//        for (PlanEntity plan : plans) {
//            repo.insertPlan(plan)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(() -> {
//                        Log.d("PlanPresenter", "Data inserted successfully: " + plan.getMealName());
//                    }, throwable -> {
//                        Log.e("PlanPresenter", "Error inserting data", throwable);
//                    });
//        }
//    }
//
//    public void loadMealsForDay(String weekday) {
//        repo.getAllPlansByDay(weekday).observe(view.getLifecycleOwner(), meals -> {
//            if (meals != null) {
//                view.updateMealDataList(meals);
//            } else {
//                view.updateMealDataList(new ArrayList<>());
//            }
//        });
//    }
//
//    public void deletePlan(PlanEntity planEntity) {
//        repo.deletePlan(planEntity)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> {
//                    Log.d("PlanPresenter", "Data deleted successfully: " + planEntity.getMealName());
//                }, throwable -> {
//                    Log.e("PlanPresenter", "Error deleting data", throwable);
//                });
//    }
//}
//
