package com.example.mealplanner.ui.home.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mealplanner.R;
import com.example.mealplanner.data.model.AreaData;
import com.example.mealplanner.data.model.CategorieData;

import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    List<AreaData> areas;
    OnAreaClickListener listener;
    public AreaAdapter(List<AreaData>areas , OnAreaClickListener listener) {
        this.areas = areas;
        this.listener = listener;
    }

    public void set(List<AreaData>areas) {
        this.areas = areas;
    }

    public void updatedata(List<AreaData>areas) {
        this.areas = areas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.search_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.areaName.setText(areas.get(position).getAreaName());

        AreaData data = areas.get(position);

        holder.bind(data, listener);
    }


    @Override
    public int getItemCount() {
        return areas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView areaName;
        ImageView areaImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            areaName = itemView.findViewById(R.id.search_card_title);
            areaImage = itemView.findViewById(R.id.search_card_img);
        }

        public void bind(AreaData areaData , OnAreaClickListener listener)
        {
            itemView.setOnClickListener(v -> listener.onAreaClick(areaData));
        }
    }

    public interface OnAreaClickListener {
        void onAreaClick(AreaData areaData );
    }
}