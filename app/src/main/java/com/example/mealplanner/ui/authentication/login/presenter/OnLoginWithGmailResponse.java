package com.example.mealplanner.ui.authentication.login.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface OnLoginWithGmailResponse {
    void onLoginWithGmailSuccess(GoogleSignInAccount account);
    void onLoginWithGmailError(String error);
}