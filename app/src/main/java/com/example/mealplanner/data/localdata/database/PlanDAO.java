package com.example.mealplanner.data.localdata.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PlanDAO {
    @Query("SELECT * FROM PlanMeals WHERE weekDay = :weekday")
    LiveData<List<PlanEntity>> getMealsForDay(String weekday);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlan(PlanEntity plan);

    @Delete
    void deletePlan(PlanEntity plan);

}
