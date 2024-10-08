package com.example.mealplanner.ui.authentication.login.presenter;

public interface LoginPresenter {
    void signIn(String email, String password);
    public void signInUsingGmailAccount(String idToken);
    public void signInAnonymously();
}
