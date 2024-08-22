package com.example.mealplanner.data.model;

import com.google.gson.annotations.SerializedName;

public class AreaData {

    @SerializedName("strArea")
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
