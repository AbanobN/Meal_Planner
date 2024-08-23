package com.example.mealplanner.ui.home.plan.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealplanner.R;
import com.example.mealplanner.data.localdata.database.MealEntity;
import com.example.mealplanner.data.localdata.database.PlanEntity;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {
    private List<PlanEntity> mealDataList;
    private OnMealClickListener listener;

    public MealAdapter(List<PlanEntity> mealDataList, OnMealClickListener listener) {
        this.mealDataList = mealDataList;
        this.listener = listener;
    }

    public void updateMealDataList(List<PlanEntity> newMealDataList) {
        this.mealDataList = newMealDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        PlanEntity data = mealDataList.get(position);
        holder.bind(data, listener);
    }

    @Override
    public int getItemCount() {
        return mealDataList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        FrameLayout cancelButton;
        Button addToFavBtn;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.fav_title);
            mealImage = itemView.findViewById(R.id.fav_img);
            cancelButton = itemView.findViewById(R.id.cancel_btn);
            addToFavBtn = itemView.findViewById(R.id.addToFavoritesBtn);
        }

        public void bind(PlanEntity data, OnMealClickListener listener) {
            mealName.setText(data.getMealName());
            Glide.with(itemView.getContext()).load(data.getMealUrlImg()).into(mealImage);
            itemView.setOnClickListener(v -> listener.onMealClick(data));
            cancelButton.setOnClickListener(v -> {
                // Implement the cancel button action
                listener.onMealCancel(data); // Ensure this method exists in OnMealClickListener
            });
        }
    }

    public interface OnMealClickListener {
        void onMealClick(PlanEntity mealEntity);
        void onMealCancel(PlanEntity mealEntity); // Add this method if needed
    }
}
