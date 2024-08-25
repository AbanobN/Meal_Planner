package com.example.mealplanner.ui.home.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.data.model.CategorieData;
import com.example.mealplanner.data.model.MealData;
import com.example.mealplanner.R;
import com.example.mealplanner.data.model.MealEntity;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<MealData> mealDataList;
    private List<MealEntity> favoritesMeal = new ArrayList<>();
    private OnMealClickListener listener;

    public MealAdapter(List<MealData> mealDataList , OnMealClickListener listener) {
        this.mealDataList = mealDataList;
        this.listener = listener;
    }

    public void updateMealDataList(List<MealData> newMealDataList) {
        this.mealDataList = newMealDataList;
        notifyDataSetChanged();
    }

    public void updateFavorites(List<MealEntity> favMealList)
    {
        this.favoritesMeal = favMealList;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealData data = mealDataList.get(position);

        if (isMealFavorite(data.getIdMeal())) {
            holder.favBtn.setSelected(true);
        } else {
            holder.favBtn.setSelected(false);
        }

        // Set onClickListener for favorite button
        holder.favBtn.setOnClickListener(v -> {
            if (isMealFavorite(data.getIdMeal())) {
                listener.deleteFromFav(data);
                holder.favBtn.setSelected(false);
            } else {
                listener.insertIntoFav(data);
                holder.favBtn.setSelected(true);
            }
        });

        holder.bind(data , listener);
    }

    @Override
    public int getItemCount() {
        return mealDataList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        ImageButton favBtn;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealCard_title);
            mealImage = itemView.findViewById(R.id.mealCard_img);
            favBtn = itemView.findViewById(R.id.favButton);

        }

        public void bind(MealData data , OnMealClickListener listener) {
            mealName.setText(data.getStrMeal());
            Glide.with(itemView.getContext()).load(data.getStrMealThumb()).into(mealImage);
            itemView.setOnClickListener(view -> listener.onMealClick(new MealEntity(data.getIdMeal(),data.getStrMeal(),data.getStrMealThumb(),"")));

        }
    }

    public boolean isMealFavorite(String mealId) {
        for (MealEntity favMeal : favoritesMeal) {
            if (favMeal.getId().equals(mealId)) {
                return true;
            }
        }
        return false;
    }

    public interface OnMealClickListener {
        void onMealClick(MealEntity mealEntity);
        void deleteFromFav(MealData meal);
        void insertIntoFav(MealData meal);
    }
}
