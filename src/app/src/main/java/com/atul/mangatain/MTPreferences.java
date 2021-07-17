package com.atul.mangatain;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class MTPreferences {
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                MTConstants.PACKAGE_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.edit();
    }

    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(
                MTConstants.PACKAGE_NAME, Context.MODE_PRIVATE
        );
    }

    public static void storeTheme(Context context, int theme) {
        getEditor(context).putInt(MTConstants.SETTINGS_THEME, theme).apply();
    }

    public static int getTheme(Context context) {
        return getSharedPref(context).getInt(MTConstants.SETTINGS_THEME, R.color.blue);
    }

    public static void storeThemeMode(Context context, int theme) {
        getEditor(context).putInt(MTConstants.SETTINGS_THEME_MODE, theme).apply();
    }

    public static int getThemeMode(Context context) {
        return getSharedPref(context).getInt(MTConstants.SETTINGS_THEME_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
