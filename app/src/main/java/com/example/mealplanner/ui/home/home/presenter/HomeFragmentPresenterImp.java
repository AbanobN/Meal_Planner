package com.example.mealplanner.ui.home.home.presenter;

import android.content.Context;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.home.view.HomeFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragmentPresenterImp {
    private HomeFragment view;
    private AppRepo repo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HomeFragmentPresenterImp(Context context, HomeFragment view) {
        this.view = view;
        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
    }

    public void getCategories(){
    compositeDisposable.add(
            repo.getAllCategories() // This internally calls fetchCategories
            .subscribe(
            categories -> view.handleCategories(categories),
            throwable -> view.handError(throwable)
            )
        );
    }

    public void getMealsByCategories(String cat)
    {
        compositeDisposable.add(
                repo.getMealsByCategory(cat)
                        .subscribe(
                                meals -> view.handleMealsByCategory(meals), // Define how to show meals in your view
                                throwable -> view.handError(throwable)
                        )
        );
    }

    public void getRandomMeal() {
        compositeDisposable.add(
                repo.getRandomMeal()
                        .subscribe(
                                meal -> view.handleRandomCard(meal),
                                throwable -> view.handError(throwable)
                        )
        );
    }

}
