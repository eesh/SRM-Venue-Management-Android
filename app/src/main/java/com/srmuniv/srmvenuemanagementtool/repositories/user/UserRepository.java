package com.srmuniv.srmvenuemanagementtool.repositories.user;

import android.util.Log;

import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository instance;
    private UserDataSource networkSource;
    private UserDataSource localSource;
    private User user;

    private UserRepository(UserDataSource userLocalRepository, UserDataSource userNetworkRepository) {
        this.localSource = userLocalRepository;
        this.networkSource = userNetworkRepository;
    }

    public static UserRepository getInstance(UserDataSource userLocalRepository, UserDataSource userNetworkRepository) {
        if(instance == null) {
            instance = new UserRepository(userLocalRepository, userNetworkRepository);
        }
        return instance;
    }

    public static UserRepository getInstance() {
        return instance;
    }

    @Override
    public void storeUser(User user, StoreUserCallback callback) {
        localSource.storeUser(user, callback);
    }

    @Override
    public void createUser(User user, final GetUserCallback callback) {
        networkSource.createUser(user, new GetUserCallback() {
            @Override
            public void onUserLoaded(User _user) {
                instance.user = _user;
                localSource.createUser(_user, new GetUserCallback() {
                    @Override
                    public void onUserLoaded(User user) {
                        callback.onUserLoaded(user);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void deleteUser(String userId, GetUserCallback callback) {

    }

    @Override
    public void getUser(final GetUserCallback callback) {
        if(user == null) {
            localSource.getUser(new GetUserCallback() {
                @Override
                public void onUserLoaded(User user) {
                    instance.user = user;
                    if(callback == null) {
                        return;
                    }
                    callback.onUserLoaded(user);
                }

                @Override
                public void onDataNotAvailable() {
                    Log.e(this.getClass().getSimpleName(), "User not found in local source");
                    networkSource.getUser(new GetUserCallback() {
                        @Override
                        public void onUserLoaded(User user) {
                            instance.user = user;
                            localSource.storeUser(user, null);
                            callback.onUserLoaded(user);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            callback.onDataNotAvailable();
                        }
                    });
                }
            });
        } else callback.onUserLoaded(user);
    }

    @Override
    public void getUser(String userId, GetUserCallback callback) {

    }

    public boolean isAdmin() {
        if (user != null) {
            if (user.getRole().equals("admin")) return true;
        }
        return false;
    }
}
