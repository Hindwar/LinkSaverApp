package com.example.rekhahindwar.applinksaver;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rekha Hindwar on 7/17/2017.
 */

class SharedPreference {
    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }
    public void saveFavorites(Context context, List<String> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }
    public void addTextView(Context context, String content) {
        List<String> favorites = createTextViewList(context);
        if (favorites == null)
            favorites = new ArrayList<String>();
        favorites.add(content);
        saveFavorites(context, favorites);
    }
    public ArrayList<String> createTextViewList(Context context){
        SharedPreferences settings;
        List<String> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            String[] favoriteItems = gson.fromJson(jsonFavorites,
                    String[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<String>(favorites);
            return (ArrayList<String>) favorites;
        }else
            return null;

    }
}
