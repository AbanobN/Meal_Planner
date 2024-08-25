package com.example.mealplanner.data.localdata.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

@Dao
public interface PlanDAO {
    @Query("SELECT * FROM PlanMeals WHERE weekDay = :weekDay AND userEmail = :userEmail")
    LiveData<List<PlanEntity>> getPlansByDayAndUser(String weekDay, String userEmail);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlan(PlanEntity plan);

    @Delete
    void deletePlan(PlanEntity plan);

    @Query("SELECT * FROM PlanMeals WHERE userEmail = :userEmail")
    List<PlanEntity> getPlansByUserEmail(String userEmail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlans(List<PlanEntity> planEntities);

}
