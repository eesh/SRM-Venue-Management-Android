package com.srmuniv.srmvenuemanagementtool.Repositories.Venue.network;

import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by eesh on 9/29/17.
 */

public class VenueClient {

    private static VenueAPI client;
    private static String API_BASE_URL = "https://eesh.me:6767/";

    private  VenueClient() {
    }

    public static VenueAPI getClient() {

        if(client != null) {
            return client;
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
        client = retrofit.create(VenueAPI.class);
        return client;
    }


    interface VenueAPI {

        @GET("venues")
        Call<List<Venue>> getVenues();

        @GET("venue")
        Call<Venue> getVenueById(String id);

        @POST("venue")
        Call<Venue> createVenue(@Body Venue params);

    }
}
