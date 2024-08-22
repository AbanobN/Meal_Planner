package com.example.mealplanner.ui.home.details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.ui.home.details.presenter.DetailsFragmentPresenterImp;

import java.util.ArrayList;

public class DetailsFragment extends Fragment  implements DetailsFragmentView{
    private ImageView mealImage;
    private TextView mealTitle, instructions;
    private VideoView mealVideo;

    private RecyclerView ingredientRecyclerView;
    IngredientAdapter ingredientadapter;

    private Button addToCalendarBtn, addToFavoritesBtn;

    DetailsFragmentPresenterImp presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mealImage = view.findViewById(R.id.mealImage);
        mealTitle = view.findViewById(R.id.mealTitle);
        instructions = view.findViewById(R.id.instructions);
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecyclerView);
        mealVideo = view.findViewById(R.id.mealVideo);
        addToCalendarBtn = view.findViewById(R.id.addToCalendarBtn);
        addToFavoritesBtn = view.findViewById(R.id.addToFavoritesBtn);


        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        ingredientadapter = new IngredientAdapter(new ArrayList<>());
        ingredientRecyclerView.setAdapter(ingredientadapter);

        presenter = new DetailsFragmentPresenterImp(getContext(),this);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DetailsFragmentArgs args = DetailsFragmentArgs.fromBundle(getArguments());
        String mealId = args.getMeaId();

        if(args.getUserEmail() != null)
        {
            Log.d("TAG1", "onViewCreated: " + args.getUserEmail());
        }

        presenter.getMealById(mealId);
    }

    @Override
    public void updateDetails(MealData mealData)
    {
        ingredientadapter.updateIngredientDataList(mealData.getIngredients());

        mealTitle.setText(mealData.getStrMeal());


        instructions.setText(mealData.getStrInstructions());

        Glide.with(requireContext())
                .load(mealData.getStrMealThumb())
                .into(mealImage);


        if(mealData.getStrYoutube() != null)
        {
            mealVideo.setVideoPath(mealData.getStrYoutube());
            mealVideo.start();
        }

    }

    @Override
    public void showError(Throwable t)
    {

    }
}