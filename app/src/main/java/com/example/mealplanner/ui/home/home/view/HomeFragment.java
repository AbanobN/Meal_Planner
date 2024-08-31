package com.example.mealplanner.ui.home.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.data.repo.AppRepo;
import com.example.mealplanner.data.repo.RepositoryProvider;
import com.example.mealplanner.ui.home.home.presenter.HomeFragmentPresenterImp;

public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener, MealAdapter.OnMealClickListener  , HomeFragmentView {
    TextView type;
    CategoryAdapter categoryAdapter;
    MealAdapter mealAdapter;
    HomeFragmentPresenterImp presenter;
    TextView randomMealText;
    ImageView randomMealImg;
    CardView randomCard;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AppRepo repo =(AppRepo) RepositoryProvider.provideRepository(getContext());
        presenter = new HomeFragmentPresenterImp(repo,this);
        presenter.getRandomMeal();
        presenter.loadAllMeals();


        RecyclerView recyclerView = view.findViewById(R.id.cat_rec_view);
        RecyclerView recyclerView2 = view.findViewById(R.id.meal_rec_view);
        type = view.findViewById(R.id.type);
        randomCard = view.findViewById(R.id.random_card);
        randomMealText = view.findViewById(R.id.mealCard_title);
        randomMealImg = view.findViewById(R.id.mealCard_img);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryAdapter = new CategoryAdapter(new ArrayList<>(),this);
        mealAdapter = new MealAdapter(new ArrayList<>() , this);

        recyclerView.setAdapter(categoryAdapter);
        recyclerView2.setAdapter(mealAdapter);

        presenter.getCategories();
        presenter.getMealsByCategories("Beef");
        type.setText("Beef Meals");


    }

    @Override
    public void onCategoryClick(CategorieData categorieData) {
        type.setText(categorieData.getStrCategory() + " Meals");
        presenter.getMealsByCategories(categorieData.getStrCategory());
    }

    @Override
    public void handleCategories(List<CategorieData> categories)
    {
        categoryAdapter.updateCategoryDataList(categories);
    }

    @Override
    public void handleMealsByCategory(List<MealData> meals)
    {
        mealAdapter.updateMealDataList(meals);
    }

    @Override
    public void handError(Throwable t)
    {
        Toast.makeText(getContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(getContext(), msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateFavoriteList(List<MealEntity> favMeals)
    {
        mealAdapter.updateFavorites(favMeals);
    }

    @Override
    public void handleRandomCard(MealData mealData)
    {
        randomMealText.setText(mealData.getStrMeal());
        Glide.with(getContext()).load(mealData.getStrMealThumb()).into(randomMealImg);

        randomCard.setOnClickListener(v ->{
            HomeFragmentDirections.ActionHomeToDetailsFragment action =
                    HomeFragmentDirections.actionHomeToDetailsFragment(mealData.getIdMeal(),"Home");
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(action);
        });
    }

    @Override
    public void onMealClick(MealEntity mealEntity) {
        HomeFragmentDirections.ActionHomeToDetailsFragment action =
                HomeFragmentDirections.actionHomeToDetailsFragment(mealEntity.getId(),"Home");
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(action);
    }

    @Override
    public void deleteFromFav(MealData meal) {
        presenter.removeFromFavorite(new MealEntity(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),""));
    }

    @Override
    public void insertIntoFav(MealData meal) {
        presenter.addToFavorite(new MealEntity(meal.getIdMeal(),meal.getStrMeal(),meal.getStrMealThumb(),""));
    }

}