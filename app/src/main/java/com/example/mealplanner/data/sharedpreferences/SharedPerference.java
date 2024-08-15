package com.example.mealplanner.data.sharedpreferences;

public interface SharedPerference {
    public void addToPreferences(String email , String pass);
    public boolean readFromPreferences();
}
