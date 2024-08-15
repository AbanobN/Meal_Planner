package com.example.mealplanner.ui.authentication.signup.view;

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
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.ui.authentication.signup.presenter.SignupPresenter;
import com.example.mealplanner.ui.authentication.signup.presenter.SignupPresenterImpl;
import com.example.mealplanner.ui.home.HomeActivity;

public class SignupFragment extends Fragment implements SignupView{

    private SignupPresenter presenter;
    private EditText userEmail;
    private EditText userPass;
    private EditText userRePass;
    private Button signUpBtn;
    private TextView loginBtn;
    Context view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        AppRepo model = AppRepo.getInstance(new AuthModelImpl(), SharedPerferencesImp.getInstance(view.getContext()));
        presenter = new SignupPresenterImpl(this, model);

        this.view = view.getContext();
        userEmail = view.findViewById(R.id.semailFeild);
        userPass = view.findViewById(R.id.passwordField);
        userRePass = view.findViewById(R.id.repasswordField);
        signUpBtn = view.findViewById(R.id.ssginupBtn);
        loginBtn = view.findViewById(R.id.sloginBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = userEmail.getText().toString();
                String userpass = userPass.getText().toString();
                String userrePass = userRePass.getText().toString();
                presenter.signUp(useremail,userpass,userrePass);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
            }
        });

    }

    @Override
    public void onSignUpSuccess() {
        // handel later
        Toast.makeText(view, "SignUp successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSignUpFailure(String error) {
        // handel later
        Toast.makeText(view, "SignUp Failed " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passwordDoNotMatch() {
        Toast.makeText(view, "Password Don't Match", Toast.LENGTH_SHORT).show();
    }
}