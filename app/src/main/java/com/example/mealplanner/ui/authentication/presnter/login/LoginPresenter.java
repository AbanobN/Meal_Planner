package com.example.mealplanner.ui.authentication.presnter.login;

import com.example.mealplanner.data.repo.NetworkCallback;

public interface LoginPresenter {
    void signIn(String email, String password);
}
