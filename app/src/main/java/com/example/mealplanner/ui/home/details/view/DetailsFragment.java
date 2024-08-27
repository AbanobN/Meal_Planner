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
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.data.model.MealEntity;
import com.example.mealplanner.ui.home.details.presenter.DetailsFragmentPresenterImp;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class DetailsFragment extends Fragment  implements DetailsFragmentView{
    private ImageView mealImage;
    private TextView mealTitle, instructions;

    private RecyclerView ingredientRecyclerView;
    private IngredientAdapter ingredientadapter;
    private YouTubePlayerView mealYoutubeVideo;
    MealEntity theMeal;

    private Button addToPlanBtn, addToFavoritesBtn;

    DetailsFragmentPresenterImp presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mealImage = view.findViewById(R.id.mealImage);
        mealTitle = view.findViewById(R.id.mealTitle);
        instructions = view.findViewById(R.id.instructions);
        ingredientRecyclerView = view.findViewById(R.id.ingredientRecyclerView);
        mealYoutubeVideo = view.findViewById(R.id.mealVideo);
        addToPlanBtn = view.findViewById(R.id.addToCalendarBtn);
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

        if(args.getScreenCameFrom().equals("Favorites"))
        {
            addToFavoritesBtn.setVisibility(View.GONE);
        }
        else if (args.getScreenCameFrom().equals("Plan")) {
            addToPlanBtn.setVisibility(View.GONE);

        }

        presenter.getMealById(mealId);

        addToFavoritesBtn.setOnClickListener(v -> presenter.addToFavorite(theMeal));



        addToPlanBtn.setOnClickListener(v -> {
            WeekPickerDialog weekPickerDialog = new WeekPickerDialog(getContext(), selectedDate -> {
                Log.d("SelectedDate", "onClick: " + selectedDate);
                presenter.addToplan(theMeal,selectedDate);
            });
            weekPickerDialog.show();

        });

    }

    @Override
    public void updateDetails(MealData mealData)
    {
        theMeal = new MealEntity(mealData.getIdMeal(),mealData.getStrMeal(),mealData.getStrMealThumb(),"");

        ingredientadapter.updateIngredientDataList(mealData.getIngredients());

        mealTitle.setText(mealData.getStrMeal());


        instructions.setText(mealData.getStrInstructions());

        Glide.with(requireContext())
                .load(mealData.getStrMealThumb())
                .into(mealImage);


        if (mealData.getStrYoutube() != null && !mealData.getStrYoutube().isEmpty()) {
            getLifecycle().addObserver(mealYoutubeVideo);
            mealYoutubeVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    //if your url is something like this -> https://www.youtube.com/watch?v=EzyXVfyx7CU
                    Log.d("MealVideo", "updateMeal:"+extractVideoId(mealData.getStrYoutube()));
                    Log.d("MealVideo", "updateMeal:"+mealData.getStrYoutube());

                    youTubePlayer.loadVideo(extractVideoId(mealData.getStrYoutube()), 0);
                }
            });
        }

    }

    @Override
    public  String extractVideoId(String url) {
        String[] parts = url.split("\\?");
        if (parts.length > 1) {
            String query = parts[1];
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                // Split each parameter on '=' to separate key and value
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && "v".equals(keyValue[0])) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }

    @Override
    public void showToast(String msg)
    {
        Toast.makeText(getContext(), msg , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable t)
    {
        Toast.makeText(getContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}