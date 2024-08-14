package com.example.mealplanner.ui.authentication.view.lgoin;

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
import com.example.mealplanner.ui.authentication.presnter.login.LoginPresenter;
import com.example.mealplanner.ui.authentication.presnter.login.LoginPresenterImpl;

public class LoginFragment extends Fragment implements LoginView{

    private LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthModel model = new AuthModelImpl();
        presenter = new LoginPresenterImpl(this, model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
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
    public void onSignInSuccess() {
        // handel later
    }

    @Override
    public void onSignInFailure(String error)
    {
        // handel later
    }
}