package com.srmuniv.srmvenuemanagementtool.Repositories.User;

import com.srmuniv.srmvenuemanagementtool.models.Token;
import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public interface UserDataSource {

    void storeAuthToken(String token, long expiry);

    void storeUser(User user, StoreUserCallback callback);

    void getAuthToken(GetTokenCallback callback);

    interface StoreUserCallback {

        void onUserStored();

        void onError();
    }

    interface GetUserCallback {

        void onUserLoaded(User user);

        void onDataNotAvailable();
    }

    interface GetTokenCallback {

        void onTokenLoaded(String token, long expiry);

        void onDataNotAvailable();
    }

    void createUser(User user, GetUserCallback callback);

    void deleteUser(String userId, GetUserCallback callback);

    void getUser(GetUserCallback callback);

    void getUser(String userId, GetUserCallback callback);
}
