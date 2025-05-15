package com.example.ameramain;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FavouriteManager {
    private static final String PREFS_NAME = "favourites";
    private static final String FAV_KEY = "favourites_ids";

    public static Set<Long> getFavourites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> favIds = prefs.getStringSet(FAV_KEY, new HashSet<>());
        Set<Long> favouritesSet = new HashSet<>();
        for (String id : favIds) {
            favouritesSet.add(Long.parseLong(id));
        }
        return favouritesSet;
    }

    public static void saveFavourites(Context context, Set<Long> favouritesSet) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> favIds = new HashSet<>();
        for (Long id : favouritesSet) {
            favIds.add(id.toString());
        }
        editor.putStringSet(FAV_KEY, favIds);
        editor.apply();
    }
}

