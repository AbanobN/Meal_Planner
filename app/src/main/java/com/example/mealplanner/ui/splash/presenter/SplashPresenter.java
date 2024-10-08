package com.example.mealplanner.ui.splash.presenter;

import android.content.Context;

import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.splash.view.SplashView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class SplashPresenter implements SplashPre{
    private SplashView _view;
    private AppRepo repo;

    public SplashPresenter(AppRepo repo, SplashView view) {
        this._view = view;
        this.repo = repo;
    }

    @Override
    public void isAppUser()
    {
        if(repo.readPreferences())
        {
            _view.isAuthentice(true);
        }
        else{
            _view.isAuthentice(false);
        }
    }

    @Override
    public void start(Long time) {
        Observable<Long> doAction = Observable.timer(time, TimeUnit.SECONDS);
        doAction.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((i) -> {
            _view.navigateToNextActivity();
        });
    }

}
