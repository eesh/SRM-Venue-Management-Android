package com.srmuniv.srmvenuemanagementtool.models.Network;

import com.google.gson.annotations.SerializedName;
import com.srmuniv.srmvenuemanagementtool.models.User;

/**
 * Created by eesh on 10/14/17.
 */

public class AuthRes {

    @SerializedName("token")
    public String token;

    @SerializedName("user")
    public User user;

    @SerializedName("expiry")
    public long expiry;

    public String getToken() {
        return token;
    }

    public long getExpiry() {
        return expiry;
    }

    public AuthRes(String token, User user, long expiry) {
        this.token = token;
        this.user = user;
        this.expiry = expiry;
    }
}
