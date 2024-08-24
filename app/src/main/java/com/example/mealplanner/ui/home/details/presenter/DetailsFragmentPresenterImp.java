//package com.example.mealplanner.ui.home.details.presenter;
//
//import android.content.Context;
//
//import com.example.mealplanner.data.repo.AppRepo;
//import com.example.mealplanner.data.repo.RepositoryProvider;
//import com.example.mealplanner.ui.home.details.view.DetailsFragment;
//
//public class DetailsFragmentPresenterImp implements DetailsFragmentPresenter{
//    DetailsFragment view;
//    AppRepo repo;
//
//    public DetailsFragmentPresenterImp(Context context, DetailsFragment view) {
//        this.view = view;
//        this.repo = (AppRepo) RepositoryProvider.provideRepository(context);
//    }
//
//    @Override
//    public void getMealById(String id)
//    {
//        repo.getMealById(id);
//    }
//
////    @Override
////    public void onMealSuccess(MealData meal) {
////        view.updateDetails(meal);
////    }
////
////    @Override
////    public void onMealFailure(Throwable t) {
////
////    }
//}
