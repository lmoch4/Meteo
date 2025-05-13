package com.example.meteo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.meteo.R;
import com.example.meteo.DAO.FavoriteCity;
import java.util.List;

public class FavoriteCityAdapter extends RecyclerView.Adapter<FavoriteCityAdapter.ViewHolder> {

    private List<FavoriteCity> favoriteCities;

    public FavoriteCityAdapter(List<FavoriteCity> favoriteCities) {
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
    }

    @Override
    public int getItemCount() {
        return favoriteCities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCityName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCityName = itemView.findViewById(R.id.textViewCityName);
        }
    }
}
