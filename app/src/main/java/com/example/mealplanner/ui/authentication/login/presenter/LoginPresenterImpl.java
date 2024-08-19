package com.example.mealplanner.ui.authentication.login.presenter;

import android.content.Context;

import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.ui.authentication.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter , FirebaseAuthCallback {
    private AppRepo model;
    private LoginView view;
    private String userEmail;
    private String userPassword;

    public LoginPresenterImpl(Context context, LoginView view) {
        this.view = view;
        this.model =AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(context) , RetrofitClient.getClient());;
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
