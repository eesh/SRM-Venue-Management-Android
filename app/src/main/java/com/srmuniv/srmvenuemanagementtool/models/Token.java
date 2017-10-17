package com.srmuniv.srmvenuemanagementtool.models;

/**
 * Created by eesh on 10/17/17.
 */

public class Token {

    String token;

    long expiry;

    public Token(String token, long expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public long getExpiry() {
        return expiry;
    }
}
