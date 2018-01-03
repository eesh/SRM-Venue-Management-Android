package com.srmuniv.srmvenuemanagementtool.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.srmuniv.srmvenuemanagementtool.network.models.AuthRes;
import com.srmuniv.srmvenuemanagementtool.models.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

public class UserClient {

    private static UserAPI client;
    final static String API_BASE_URL = "http://eesh.me:6767/user/";

    public static UserAPI getClient(final String authToken) {
        if(client != null) {
            return client;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new CustomDeserializer<User>("userDetails")).create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("token", authToken);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create(gson)
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient
                        )
                        .build();
        client = retrofit.create(UserAPI.class);
        return client;
    }

    public interface UserAPI {

        @POST("profile")
        Call<User> getProfile();
    }
}
