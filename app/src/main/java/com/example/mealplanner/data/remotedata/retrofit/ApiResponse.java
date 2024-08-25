package com.example.mealplanner.data.remotedata.retrofit;

import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.IngredientData;
import com.example.mealplanner.data.model.MealData;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {

    public static class MealResponse {
        @SerializedName("meals")
        private List<MealData> meals;

        public List<MealData> getMeals() {
            return meals;
        }

        public void setMeals(List<MealData> meals) {
            this.meals = meals;
        }
    }

    public static class CategoryResponse {
        @SerializedName("categories")
        private List<CategorieData> categories;

        public List<CategorieData> getCategories() {
            return categories;
        }

        public void setCategories(List<CategorieData> categories) {
            this.categories = categories;
        }
    }

    public static class ListResponse {
        @SerializedName("meals")
        private List<MealData> meals;

        public List<MealData> getMeals() {
            return meals;
        }

        public void setMeals(List<MealData> meals) {
            this.meals = meals;
        }
    }

    public class IngredientResponse {
        @SerializedName("meals")
        private List<IngredientData> ingredientData;

        public List<IngredientData> getIngredients() {
            return ingredientData;
        }
    }

    public static class AreaResponse {
        @SerializedName("meals")
        private List<AreaData> areas;

        public List<AreaData> getAreas() {
            return areas;
        }

        public void setAreas(List<AreaData> areas) {
            this.areas = areas;
        }
    }
}
