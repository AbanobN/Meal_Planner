package com.example.mealplanner.data.model;

import com.google.gson.annotations.SerializedName;

public class IngredientData {

    @SerializedName("strIngredient")
    private String name;

    public IngredientData(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

}
