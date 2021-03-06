package com.srmuniv.srmvenuemanagementtool.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.srmuniv.srmvenuemanagementtool.network.models.AuthRes;
import com.srmuniv.srmvenuemanagementtool.models.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by eesh on 10/14/17.
 */

public class AuthClient {

    private static AuthAPI client;
    final static String API_BASE_URL = "http://eesh.me:6767/auth/";

    public static AuthAPI getClient() {
        if(client != null) {
            return client;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(AuthRes.class, new CustomDeserializer<AuthRes>("tokenDetails"))
                .registerTypeAdapter(User.class, new CustomDeserializer<User>("userDetails")).create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit builder = new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(API_BASE_URL)
                .build();
        client = builder.create(AuthAPI.class);
        return client;
    }

    public interface AuthAPI {

        @FormUrlEncoded
        @POST("register")
        Call<User> registerUser(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("department") String department);

        @FormUrlEncoded
        @POST("login")
        Call<AuthRes> loginUser(@Field("email") String email, @Field("password") String password);
    }
}
