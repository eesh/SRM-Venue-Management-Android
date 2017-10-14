package com.srmuniv.srmvenuemanagementtool.Repositories.User;

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
                    callback.onUserLoaded(user);
                }

                @Override
                public void onDataNotAvailable() {
                    networkSource.getUser(new GetUserCallback() {
                        @Override
                        public void onUserLoaded(User user) {
                            instance.user = user;
                            callback.onUserLoaded(user);
                        }

                        @Override
                        public void onDataNotAvailable() {
                            callback.onDataNotAvailable();
                        }
                    });
                }
            });
        }
    }

    @Override
    public void getUser(String userId, GetUserCallback callback) {

    }
}
