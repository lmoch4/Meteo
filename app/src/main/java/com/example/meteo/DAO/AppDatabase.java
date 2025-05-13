package com.example.meteo.DAO;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteCity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteCityDao favoriteCityDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "favorite_cities_db")
                            .allowMainThreadQueries() // À éviter en production
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
