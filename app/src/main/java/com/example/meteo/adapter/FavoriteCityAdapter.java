package com.example.meteo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meteo.MainActivity;
import com.example.meteo.R;
import com.example.meteo.DAO.FavoriteCity;


import java.util.List;

public class FavoriteCityAdapter extends RecyclerView.Adapter<FavoriteCityAdapter.ViewHolder> {

    private final List<FavoriteCity> favoriteCities;
    private final Context context;

    public FavoriteCityAdapter(Context context, List<FavoriteCity> favoriteCities) {
        this.context = context;
        this.favoriteCities = favoriteCities;
    }

    @NonNull
    @Override
    public FavoriteCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteCityAdapter.ViewHolder holder, int position) {
        FavoriteCity city = favoriteCities.get(position);
        holder.textViewCityName.setText(city.cityName);

        holder.layoutCityItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("city_name",city.getCityName());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return favoriteCities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCityName;
        LinearLayout layoutCityItem;
        ImageButton buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCityName = itemView.findViewById(R.id.textViewCityName);
            layoutCityItem = itemView.findViewById(R.id.layoutCityItem);

        }
    }
}
