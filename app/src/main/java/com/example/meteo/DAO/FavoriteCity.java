package com.example.meteo.DAO;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteCity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String cityName;

    public String getCityName() {
        return cityName;
    }
}
