package com.example.mealplanner.data.model;

public abstract class BaseEntity {

    private String id;
    private String mealName;
    private String mealUrlImg;
    private String userEmail;

    public BaseEntity() {
        // Default constructor
    }

    public BaseEntity(String id, String mealName, String mealUrlImg, String userEmail) {
        this.id = id;
        this.mealName = mealName;
        this.mealUrlImg = mealUrlImg;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealUrlImg() {
        return mealUrlImg;
    }

    public void setMealUrlImg(String mealUrlImg) {
        this.mealUrlImg = mealUrlImg;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
