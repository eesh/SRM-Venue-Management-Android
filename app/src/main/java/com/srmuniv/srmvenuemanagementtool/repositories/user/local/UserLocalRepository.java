package com.srmuniv.srmvenuemanagementtool.repositories.user.local;

import android.content.Context;
import android.util.Log;

import com.srmuniv.srmvenuemanagementtool.repositories.user.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.models.User;

import java.util.Date;

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
    public void storeUser(User user, StoreUserCallback callback) {
        preferencesHelper.storeUser(user);
        if(callback != null) {
            callback.onUserStored();
        }
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
        if (user == null) {
            callback.onDataNotAvailable();
            return;
        }
        callback.onUserLoaded(user);
    }

    @Override
    public void getUser(String userId, GetUserCallback callback) {
        getUser(callback);
    }
}
