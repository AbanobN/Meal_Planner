package com.example.mealplanner.ui.authentication.login.presenter;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.NetworkCallback;
import com.example.mealplanner.ui.authentication.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter , NetworkCallback {
    private AppRepo model;
    private LoginView view;
    private String userEmail;
    private String userPassword;

    public LoginPresenterImpl(LoginView view, AppRepo model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onSuccess() {
        model.writePrefernces(userEmail,userPassword);
        view.onSignInSuccess();
    }

    @Override
    public void onFailure(Exception e) {
        view.onSignInFailure(e.getMessage());
    }

    @Override
    public void signIn(String email, String password) {
        userEmail = email;
        userPassword = password;
        model.signInApp(email, password, this);
    }

}
