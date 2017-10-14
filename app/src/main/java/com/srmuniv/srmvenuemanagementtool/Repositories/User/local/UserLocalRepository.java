package com.srmuniv.srmvenuemanagementtool.Repositories.User.local;

import android.content.Context;

import com.srmuniv.srmvenuemanagementtool.Repositories.User.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public class UserLocalRepository implements UserDataSource {

    private static UserLocalRepository instance;
    private PreferencesHelper preferencesHelper;

    private UserLocalRepository(Context context) {
        preferencesHelper = PreferencesHelper.getInstance(context);
    }

    public static UserLocalRepository getInstance(Context context) {
        if(instance == null) {
            instance = new UserLocalRepository(context);
        }
        return instance;
    }

    @Override
    public void createUser(User user, GetUserCallback callback) {
        preferencesHelper.storeUser(user);
        callback.onUserLoaded(user);
    }

    @Override
    public void deleteUser(String userId, GetUserCallback callback) {
        User user = preferencesHelper.getUser();
        preferencesHelper.clearUser();
        callback.onUserLoaded(user);
    }

    @Override
    public void getUser(GetUserCallback callback) {
        User user = preferencesHelper.getUser();
        callback.onUserLoaded(user);
    }

    @Override
    public void getUser(String userId, GetUserCallback callback) {
        getUser(callback);
    }
}
