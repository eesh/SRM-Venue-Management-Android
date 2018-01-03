package com.srmuniv.srmvenuemanagementtool.repositories.authentication.local;

import android.content.Context;

import com.srmuniv.srmvenuemanagementtool.repositories.authentication.AuthDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.user.local.*;

import java.util.Date;

/**
 * Created by eesh on 1/4/18.
 */

public class AuthLocalRepository implements AuthDataSource {


    private static AuthLocalRepository instance;
    private PreferencesHelper preferencesHelper;

    private AuthLocalRepository(Context context) {
        preferencesHelper = PreferencesHelper.getInstance(context);
    }

    public static AuthLocalRepository getInstance(Context context) {
        if(instance == null) {
            instance = new AuthLocalRepository(context);
        }
        return instance;
    }

    @Override
    public void storeAuthToken(String token, long expiry) {
        preferencesHelper.storeAuthToken(token, expiry);
    }

    @Override
    public void getAuthToken(GetTokenCallback callback) {
        String token = preferencesHelper.getAuthToken();
        long expiry = preferencesHelper.getTokenExpiry();
        if(token.length() == 0) {
            callback.onDataNotAvailable();
        } else if (new Date().getTime() > expiry) {
            callback.onDataNotAvailable();
        } else {
            callback.onTokenLoaded(token, expiry);
        }
    }
}
