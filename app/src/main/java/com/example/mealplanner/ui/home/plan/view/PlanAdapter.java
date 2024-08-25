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
import com.example.mealplanner.data.model.PlanEntity;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.PlanViewHolder> {
    private List<PlanEntity> planDataList;
    private OnPlanClickListener listener;

    public PlanAdapter(List<PlanEntity> planDataList, OnPlanClickListener listener) {
        this.planDataList = planDataList;
        this.listener = listener;
    }

    public void updatePlanDataList(List<PlanEntity> newPlanDataList) {
        this.planDataList = newPlanDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_card, parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        PlanEntity data = planDataList.get(position);
        holder.bind(data, listener);
    }

    @Override
    public int getItemCount() {
        return planDataList.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {
        TextView planName;
        ImageView planImage;
        FrameLayout cancelButton;
        Button addToFavBtn;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            planName = itemView.findViewById(R.id.plan_title);
            planImage = itemView.findViewById(R.id.plan_img);
            cancelButton = itemView.findViewById(R.id.cancel_btn);
            addToFavBtn = itemView.findViewById(R.id.addToFavoritesBtn);
        }

        public void bind(PlanEntity data, OnPlanClickListener listener) {
            planName.setText(data.getMealName()); // Assuming PlanEntity has a getMealName() method
            Glide.with(itemView.getContext()).load(data.getMealUrlImg()).into(planImage); // Assuming PlanEntity has a getMealUrlImg() method
            itemView.setOnClickListener(v -> listener.onPlanClick(data));
            cancelButton.setOnClickListener(v -> listener.onPlanCancel(data));
        }
    }

    public interface OnPlanClickListener {
        void onPlanClick(PlanEntity planEntity);
        void onPlanCancel(PlanEntity planEntity); // Assuming you want to keep this method
    }
}
