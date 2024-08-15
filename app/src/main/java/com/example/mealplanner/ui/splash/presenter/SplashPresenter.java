package com.example.mealplanner.ui.splash.presenter;

import android.content.Context;

import com.example.mealplanner.data.remotedata.firebaseauth.AuthModelImpl;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.localdata.sharedpreferences.SharedPerferencesImp;
import com.example.mealplanner.ui.splash.view.SplashView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class SplashPresenter implements SplashPre{
    private SplashView _view;
    private AppRepo repo;

    public SplashPresenter(Context context, SplashView view) {
        this._view = view;
        this.repo = AppRepo.getInstance(new AuthModelImpl(),SharedPerferencesImp.getInstance(context));
    }

    public void isAppUser()
    {
        if(repo.readPrefernces())
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
