package com.example.mealplanner.ui.authentication.signup.presenter;

import android.content.Context;

import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.remotedata.retrofit.RetrofitClient;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.remotedata.firebaseauth.FirebaseAuthCallback;
import com.example.mealplanner.ui.authentication.signup.view.SignupView;

public class SignupPresenterImpl implements SignupPresenter , FirebaseAuthCallback {
    private AppRepo model;
    private SignupView view;
    private String userEmail;
    private String userPassword;

    public SignupPresenterImpl(Context context, SignupView view) {
        this.view = view;
        this.model = AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(context), RetrofitClient.getClient());;
    }

    @Override
    public void onSuccess() {
        model.writePrefernces(userEmail,userPassword);
        view.onSignUpSuccess();
    }

    @Override
    public void onFailure(Exception e) {
        view.onSignUpFailure(e.getMessage());
    }

    @Override
    public void signUp(String email, String password , String rePassword) {
        if(!password.equals(rePassword))
        {
            view.passwordDoNotMatch();
            return;
        }
        userEmail = email;
        userPassword = password;
        model.signUpApp(email, password,this);
    }

}
