package com.example.mealplanner.data.localdata.database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mealplanner.data.model.MealEntity;

import java.util.List;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM FavoritesMeals")
    Flowable<List<MealEntity>> getAll(); // Use Flowable for continuous updates

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MealEntity meal);

    @Delete
    void delete(MealEntity meal);
}
