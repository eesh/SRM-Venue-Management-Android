package com.srmuniv.srmvenuemanagementtool.Repositories.User.network;

import com.srmuniv.srmvenuemanagementtool.Repositories.User.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public class UserNetworkRepository implements UserDataSource {

    @Override
    public void storeAuthToken(String token, long expiry) {

    }

    @Override
    public void storeUser(User user, StoreUserCallback callback) {

    }

    @Override
    public void getAuthToken(GetTokenCallback callback) {

    }

    @Override
    public void createUser(User user, GetUserCallback callback) {

    }

    @Override
    public void deleteUser(String userId, GetUserCallback callback) {

    }

    @Override
    public void getUser(GetUserCallback callback) {

    }

    @Override
    public void getUser(String userId, GetUserCallback callback) {

    }
}
