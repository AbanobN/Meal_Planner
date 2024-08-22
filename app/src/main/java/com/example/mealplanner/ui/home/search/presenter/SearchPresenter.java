package com.example.mealplanner.ui.home.search.presenter;

public interface SearchPresenter {
    void getCategories();
    void getAllCountries();
    void getAllIngredients();
    public void clearDisposables();
    public void searchMeals(String query);
    void onAreaClick(String area);
    void onCategoryClick(String category);
    void onIngredientClick(String ingredient);
}
