package com.srmuniv.srmvenuemanagementtool.repositories.authentication;

/**
 * Created by eesh on 1/4/18.
 */

public class AuthRepository implements AuthDataSource {

    AuthDataSource localSource;
    static AuthRepository instance;
    private String authToken;

    private AuthRepository(AuthDataSource localSource) {
        this.localSource = localSource;
    }

    public static AuthRepository getInstance(AuthDataSource authDataSource) {
        if(instance == null) {
            instance = new AuthRepository(authDataSource);
        }
        return instance;
    }


    @Override
    public void storeAuthToken(String token, long expiry) {
        localSource.storeAuthToken(token, expiry);
        authToken = token;
    }

    @Override
    public void getAuthToken(final GetTokenCallback callback) {
        localSource.getAuthToken(new GetTokenCallback() {
            @Override
            public void onTokenLoaded(String token, long expiry) {
                callback.onTokenLoaded(token, expiry);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
