package com.example.mealplanner.data.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("strIngredient")
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

}
