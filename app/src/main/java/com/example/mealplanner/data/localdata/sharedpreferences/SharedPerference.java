package com.example.mealplanner.data.localdata.sharedpreferences;

public interface SharedPerference {
    public void addToPreferences(String email , String pass);
    public boolean readFromPreferences();
    public void removePreferences();
    public String getEmail();
}
