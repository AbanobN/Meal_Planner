package com.example.mealplanner.data.localdata.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPerferencesManger implements SharedPerference{
    private SharedPreferences setting;
    private SharedPreferences.Editor editor;
    private final String emailKey = "Email_key";
    private final String passKey = "Pass_key";
    private final String defultValue = "N/A";
    private final String shPreferenceName = "mySharedPerfernce";
    private static SharedPerferencesManger instance = null;

    private SharedPerferencesManger(Context context)
    {
        this.setting = context.getSharedPreferences(shPreferenceName, Context.MODE_PRIVATE);
        editor = setting.edit();
    }

    public static SharedPerferencesManger getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new SharedPerferencesManger(context);
        }
        return instance;
    }

    public void addToPreferences(String email , String pass)
    {
        editor.putString(emailKey , email);
        editor.putString(passKey , pass);
        editor.commit();
    }

    public void removePreferences()
    {
        editor.putString(emailKey , defultValue);
        editor.putString(passKey , defultValue);
        editor.commit();
    }

    public boolean readFromPreferences()
    {
        String userEmail = setting.getString(emailKey,defultValue);
        String userPass = setting.getString(passKey,defultValue);

        if(userEmail.equals(defultValue) || userPass.equals(defultValue))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public String getEmail()
    {
        String userEmail = setting.getString(emailKey,defultValue);
        return userEmail;
    }

}
