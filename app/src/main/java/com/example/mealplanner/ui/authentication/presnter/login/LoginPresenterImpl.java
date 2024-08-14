package com.example.mealplanner.ui.authentication.presnter.login;

import com.example.mealplanner.data.firebase.AuthModel;
import com.example.mealplanner.ui.authentication.view.lgoin.LoginView;

public class LoginPresenterImpl implements LoginPresenter{
    private AuthModel model;
    private LoginView view;

    public LoginPresenterImpl(LoginView view, AuthModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void signIn(String email, String password) {
        view.showLoading();
        model.signIn(email, password, new AuthModel.AuthCallback() {
            @Override
            public void onSuccess() {
                view.hideLoading();
                view.onSignInSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                view.hideLoading();
                view.onSignInFailure(e.getMessage());
            }
        });
    }
}
