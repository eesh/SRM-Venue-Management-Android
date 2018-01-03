package com.srmuniv.srmvenuemanagementtool.repositories.authentication;

import com.srmuniv.srmvenuemanagementtool.repositories.user.UserDataSource;

/**
 * Created by eesh on 1/4/18.
 */

public interface AuthDataSource {

    void storeAuthToken(String token, long expiry);

    void getAuthToken(AuthDataSource.GetTokenCallback callback);

    interface GetTokenCallback {

        void onTokenLoaded(String token, long expiry);

        void onDataNotAvailable();
    }
}
