package com.example.mealplanner.ui.home.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.ui.home.home.presenter.HomeFragmentPresenterImp;

public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener , HomeFragmentView {
    TextView type;
    CategoryAdapter categoryAdapter;
    MealAdapter mealAdapter;
    HomeFragmentPresenterImp presenter;


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
        presenter = new HomeFragmentPresenterImp(getContext(),this);

        RecyclerView recyclerView = view.findViewById(R.id.cat_rec_view);
        RecyclerView recyclerView2 = view.findViewById(R.id.meal_rec_view);
        type = view.findViewById(R.id.type);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter(new ArrayList<>(),this);
        mealAdapter = new MealAdapter(new ArrayList<>());

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

}