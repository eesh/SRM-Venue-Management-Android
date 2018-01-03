package com.srmuniv.srmvenuemanagementtool.repositories.user.network;

import com.srmuniv.srmvenuemanagementtool.network.UserClient;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eesh on 10/14/17.
 */

public class UserNetworkRepository implements UserDataSource {

    private static UserNetworkRepository instance;
    private static UserClient.UserAPI client;

    private UserNetworkRepository(String authToken) {
        client = UserClient.getClient(authToken);
    }

    public static UserNetworkRepository getInstance(String authToken) {
        if(instance == null) {
            instance = new UserNetworkRepository(authToken);
        }
        return instance;
    }

    @Override
    public void storeUser(User user, StoreUserCallback callback) {

    }

    @Override
    public void createUser(User user, GetUserCallback callback) {

    }

    @Override
    public void deleteUser(String userId, GetUserCallback callback) {

    }

    @Override
    public void getUser(final GetUserCallback callback) {
        Call<User> call = client.getProfile();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if(response.body().getName() != null) {
                        callback.onUserLoaded(response.body());
                    } else callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getUser(String userId, GetUserCallback callback) {

    }
}
