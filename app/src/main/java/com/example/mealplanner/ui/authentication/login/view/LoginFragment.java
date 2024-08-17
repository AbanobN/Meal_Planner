package com.example.mealplanner.ui.authentication.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.ui.authentication.login.presenter.LoginPresenter;
import com.example.mealplanner.ui.authentication.login.presenter.LoginPresenterImpl;
import com.example.mealplanner.ui.home.homeactivity.view.HomeActivity;

public class LoginFragment extends Fragment implements LoginView{

    private LoginPresenter presenter;
    EditText emailField;
    EditText passwordField;
    Button loginBtn;
    TextView signupBtn;
    Context view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        AppRepo model = AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(view.getContext()));
        presenter = new LoginPresenterImpl(this, model);

        emailField = view.findViewById(R.id.emailField);
        passwordField = view.findViewById(R.id.passwordField);
        loginBtn = view.findViewById(R.id.loginBtn);
        signupBtn = view.findViewById(R.id.signupBtn);

        this.view = view.getContext();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = emailField.getText().toString();
                String userpass = passwordField.getText().toString();
                presenter.signIn(useremail,userpass);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
    }


    @Override
    public void onSignInSuccess() {
        // handel later
        Toast.makeText(view, "Login successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSignInFailure(String error)
    {
        // handel later
        Toast.makeText(view, "Login Failed", Toast.LENGTH_SHORT).show();
    }
}