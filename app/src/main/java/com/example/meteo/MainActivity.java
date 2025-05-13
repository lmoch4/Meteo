package com.example.meteo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.meteo.DAO.AppDatabase;
import com.example.meteo.DAO.FavoriteCity;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SEARCH = 1;
    private TextView textViewCity, textViewTemperature, textViewDescription;
    private ImageView imageViewIcon;
    private Button buttonSearch, buttonFavorites, buttonAddFavorite;
    private RequestQueue requestQueue;
    private final String API_KEY = "8819462e5aa1d03181fe773d53b9d018"; // Remplace par ta clé API
    private String currentCity = "Rabat"; // Ville par défaut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assure-toi que le nom du layout est correct

        // Initialisation des vues
        textViewCity = findViewById(R.id.textViewCity);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        textViewDescription = findViewById(R.id.textViewDescription);
        imageViewIcon = findViewById(R.id.imageViewIcon);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonFavorites = findViewById(R.id.buttonFavorites);
        buttonAddFavorite = findViewById(R.id.buttonAddFavorite);

        // Initialisation de la file de requêtes Volley
        requestQueue = Volley.newRequestQueue(this);

        // Chargement des données météo pour une ville par défaut
        fetchWeatherData(currentCity);

        // Écouteur pour le bouton de recherche
        buttonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SEARCH);
        });

        // Écouteur pour le bouton des favoris
        buttonFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        // Écouteur pour le bouton d'ajout aux favoris
        buttonAddFavorite.setOnClickListener(v -> {
            if (currentCity != null && !currentCity.isEmpty()) {
                AppDatabase db = AppDatabase.getInstance(this);
                FavoriteCity city = new FavoriteCity();
                city.cityName = currentCity;
                db.favoriteCityDao().insert(city);
                Toast.makeText(this, currentCity + " ajoutée aux favoris.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Aucune ville sélectionnée.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SEARCH && resultCode == RESULT_OK && data != null) {
            String city = data.getStringExtra("city");
            if (city != null && !city.isEmpty()) {
                currentCity = city;
                fetchWeatherData(city);
            } else {
                Toast.makeText(this, "Nom de ville invalide.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + API_KEY + "&units=metric&lang=fr";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        // Extraction des données de la réponse JSON
                        String cityName = response.getString("name");

                        JSONObject main = response.getJSONObject("main");
                        double temperature = main.getDouble("temp");

                        JSONArray weatherArray = response.getJSONArray("weather");
                        JSONObject weather = weatherArray.getJSONObject(0);
                        String description = weather.getString("description");
                        String iconCode = weather.getString("icon");

                        // Mise à jour de l'interface utilisateur
                        textViewCity.setText(cityName);
                        textViewTemperature.setText(String.format("%.1f°C", temperature));
                        textViewDescription.setText(description);

                        // Chargement de l'icône météo
                        String iconUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";
                        Picasso.get().load(iconUrl).into(imageViewIcon);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Erreur lors de l'analyse des données.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Erreur lors de la récupération des données.", Toast.LENGTH_SHORT).show();
                }
        );

        // Ajout de la requête à la file
        requestQueue.add(jsonObjectRequest);
    }
}
