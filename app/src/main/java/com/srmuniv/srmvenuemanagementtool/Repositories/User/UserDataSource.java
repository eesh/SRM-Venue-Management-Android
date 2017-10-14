package com.srmuniv.srmvenuemanagementtool.Repositories.User;

import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public interface UserDataSource {

    interface GetUserCallback {

        void onUserLoaded(User user);

        void onDataNotAvailable();
    }

    void createUser(User user, GetUserCallback callback);

    void deleteUser(String userId, GetUserCallback callback);

    void getUser(GetUserCallback callback);

    void getUser(String userId, GetUserCallback callback);
}
