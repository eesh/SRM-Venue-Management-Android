package com.srmuniv.srmvenuemanagementtool.repositories.user.local;

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

    private final String USER_ID = "USER_ID";
    private final String USER_DEPARTMENT = "USER_DEPARTMENT";
    private final String USER_ROLE = "USER_ROLE";
    private final String USER_NAME = "USER_NAME";
    private final String USER_EMAIL = "USER_EMAIL";

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
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID, user.getId());
        editor.putString(USER_DEPARTMENT, user.getDepartment());
        editor.putString(USER_ROLE, user.getRole());
        editor.putString(USER_NAME, user.getName());
        editor.putString(USER_EMAIL, user.getEmail());
        editor.commit();
    }

    public void clearUser() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USER_ID);
        editor.remove(USER_DEPARTMENT);
        editor.remove(USER_ROLE);
        editor.remove(USER_NAME);
        editor.remove(USER_EMAIL);
        editor.apply();
    }

    public User getUser() {
        String name = preferences.getString(USER_NAME, null);
        String email = preferences.getString(USER_EMAIL, null);
        String department = preferences.getString(USER_DEPARTMENT, null);
        String role = preferences.getString(USER_ROLE, null);
        String id = preferences.getString(USER_ID, null);
        if (name == null) {
            return null;
        } else {
            Log.e(this.getClass().getSimpleName(), "User found in preferences: " + name);
        }
        User user = new User(id, name, email, department, role);
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
