package com.srmuniv.srmvenuemanagementtool.repositories.authentication.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
