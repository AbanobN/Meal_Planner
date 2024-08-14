package com.example.mealplanner.ui.authentication.view.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealplanner.R;
import com.example.mealplanner.data.firebase.AuthModel;
import com.example.mealplanner.data.firebase.AuthModelImpl;
import com.example.mealplanner.ui.authentication.presnter.login.LoginPresenterImpl;
import com.example.mealplanner.ui.authentication.presnter.signup.SignupPresenter;
import com.example.mealplanner.ui.authentication.presnter.signup.SignupPresenterImpl;

public class SignupFragment extends Fragment implements SignupView{

    private SignupPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthModel model = new AuthModelImpl();
        presenter = new SignupPresenterImpl(this, model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showLoading() {
        // handel later
    }

    @Override
    public void hideLoading() {
        // handel later
    }

    @Override
    public void onSignUpSuccess() {
        // handel later
    }

    @Override
    public void onSignUpFailure(String error) {
        // handel later
    }
}