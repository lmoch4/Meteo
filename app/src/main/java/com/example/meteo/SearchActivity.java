package com.example.meteo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextCity;
    private Button buttonSearchCity;
    private RequestQueue requestQueue;
    private final String API_KEY = "8819462e5aa1d03181fe773d53b9d018"; // Remplace par ta clé API OpenWeatherMap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialisation des vues
        editTextCity = findViewById(R.id.editTextCity);
        buttonSearchCity = findViewById(R.id.buttonSearchCity);

        // Initialisation de la file de requêtes Volley
        requestQueue = Volley.newRequestQueue(this);

        // Écouteur pour le bouton de recherche
        buttonSearchCity.setOnClickListener(v -> {
            String city = editTextCity.getText().toString().trim();
            if (city.isEmpty()) {
                Toast.makeText(this, "Veuillez entrer un nom de ville.", Toast.LENGTH_SHORT).show();
            } else {
                fetchWeatherData(city);
            }
        });
    }

    private void fetchWeatherData(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + API_KEY + "&units=metric&lang=fr";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        // Vérifie si la réponse contient un nom de ville
                        if (response.has("name")) {
                            String cityName = response.getString("name");

                            // Retourne le nom de la ville à MainActivity
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("cityName", cityName);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(this, "Ville non trouvée.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erreur lors de l'analyse des données.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Erreur lors de la récupération des données.", Toast.LENGTH_SHORT).show();
                }
        );

        // Ajout de la requête à la file
        requestQueue.add(jsonObjectRequest);
    }
}