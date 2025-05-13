package com.example.meteo.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavoriteCityDao {
    @Insert
    void insert(FavoriteCity city);

    @Query("SELECT * FROM FavoriteCity")
    List<FavoriteCity> getAll();
}
