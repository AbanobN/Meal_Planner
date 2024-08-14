package com.example.mealplanner.ui.splash.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.mealplanner.ui.splash.view.SplashView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class SplashPresenter implements SplashPre{
    private SplashView _view;
    SharedPreferences setting;

    public SplashPresenter(Context context, SplashView view) {
        this._view = view;
        this.setting = context.getSharedPreferences("your_preference_name", Context.MODE_PRIVATE);
    }

    public void isAppUser()
    {
        String username = setting.getString("User_Name","N/A");
        String userPassword = setting.getString("User_Password","N/A");

        if(username != "N/A" && userPassword != "N/A")
        {
            _view.isAuthentice(true);
        }
        else{
            _view.isAuthentice(false);
        }
    }

    public void start(Long time) {
        Observable<Long> doAction = Observable.timer(time, TimeUnit.SECONDS);

        doAction.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((i) -> {
            _view.navigateToNextActivity();
        });
    }

}
