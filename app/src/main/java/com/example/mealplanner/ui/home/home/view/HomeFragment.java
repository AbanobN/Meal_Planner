package com.example.mealplanner.ui.home.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mealplanner.R;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;

public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryClickListener {
    TextView type;
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
        ArrayList<CategorieData> test = new ArrayList<>();
        test.add(new CategorieData("1", "Beef", "https://www.themealdb.com/images/category/beef.png"));
        test.add(new CategorieData("2", "Checken", "https://www.themealdb.com/images/category/chicken.png"));
        test.add(new CategorieData("3", "Dessert", "https://www.themealdb.com/images/category/dessert.png"));


        ArrayList<MealData> test2 = new ArrayList<>();

        test2.add(new MealData("52874", "Beef and Mustard Pie", "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg"));
        test2.add(new MealData("52878", "Beef and Oyster pie", "https://www.themealdb.com/images/media/meals/wrssvt1511556563.jpg"));
        test2.add(new MealData("53071", "Beef Asado", "https://www.themealdb.com/images/media/meals/pkopc31683207947.jpg"));

        RecyclerView recyclerView = view.findViewById(R.id.cat_rec_view);
        RecyclerView recyclerView2 = view.findViewById(R.id.meal_rec_view);
        type = view.findViewById(R.id.type);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);

        CategoryAdapter adapter = new CategoryAdapter(test,this);
        MealAdapter adapter2 = new MealAdapter(test2);

        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter2);
    }

    @Override
    public void onCategoryClick(CategorieData categorieData) {
        type.setText(categorieData.getStrCategory() + " Meals");

    }

}