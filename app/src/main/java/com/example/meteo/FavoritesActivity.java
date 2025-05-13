package com.example.meteo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.meteo.adapter.FavoriteCityAdapter;
import com.example.meteo.DAO.AppDatabase;
import com.example.meteo.DAO.FavoriteCity;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavorites;
    private FavoriteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(this));

        AppDatabase db = AppDatabase.getInstance(this);
        List<FavoriteCity> favoriteCities = db.favoriteCityDao().getAll();

        adapter = new FavoriteCityAdapter(favoriteCities);
        recyclerViewFavorites.setAdapter(adapter);
    }
}
