package com.example.mealplanner.data.repo;

import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.data.remotedata.retrofit.CategoryCallback;
import com.example.mealplanner.data.remotedata.retrofit.MealCallback;
import com.example.mealplanner.data.remotedata.retrofit.OneMealCallback;
import com.example.mealplanner.data.localdata.database.MealEntity;
import java.util.List;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Completable;

public interface Repository {
    public boolean readPrefernces();
    public void writePrefernces(String email, String password);
    public void removePrefernces();

    public void signInApp(String email, String password, FirebaseAuthCallback callback);
    public void signUpApp(String email, String password, FirebaseAuthCallback callback);
    void signOutApp();

    public void getAllCategories(CategoryCallback categoryCallback );
    public void getMealsByCategory(String category , MealCallback mealCallback);

    public void getMealById(String id , OneMealCallback oneMealCallback);

    // Add these methods to the Repository interface
    Flowable<List<MealEntity>> getAllMeals();
    Completable insertMeal(MealEntity mealEntity);
    Completable deleteMeal(MealEntity mealEntity);
}
