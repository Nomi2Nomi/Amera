package com.example.ameramain;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserManager {
    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "user_id";

    // Сохраняем userId в SharedPreferences
    public static void saveUserId(Context context, Long userId) {
        Log.d("UserManager", "Сохраняем userId: " + userId);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putLong(KEY_USER_ID, userId).apply();
    }

    // Получаем userId из SharedPreferences
    public static Long getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        long userId = prefs.getLong(KEY_USER_ID, -1);
        Log.d("UserManager", "Получен userId: " + userId);
        return userId == -1 ? null : userId;
    }


    // Очистить userId (например, при выходе из аккаунта)
    public static void clearUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_USER_ID).apply();
    }
}

