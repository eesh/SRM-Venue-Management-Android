package com.srmuniv.srmvenuemanagementtool.repositories.user.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public class PreferencesHelper {

    private static PreferencesHelper instance;
    private SharedPreferences preferences;
    final private String PREFERENCES_NAME = "preferences";

    private PreferencesHelper(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper getInstance(Context context) {
        if(instance == null) {
            instance = new PreferencesHelper(context);
        }
        return instance;
    }

    public void storeUser(User user) {

    }

    public void clearUser() {

    }

    public User getUser() {
        User user = new User(null, null, null, null);
        return user;
    }

    public void storeAuthToken(String token, long expiry) {
        preferences.edit().putString("authToken", token).putLong("token_expiry", expiry).apply();
    }

    public String getAuthToken() {
        return preferences.getString("authToken", "");
    }

    public long getTokenExpiry() {
        return preferences.getLong("token_expiry", 0);
    }
}
