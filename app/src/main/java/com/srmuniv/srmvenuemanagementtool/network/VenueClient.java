package com.srmuniv.srmvenuemanagementtool.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by eesh on 9/29/17.
 */

public class VenueClient {

    private static VenueAPI client;
    private static String API_BASE_URL = "http://eesh.me:6767/";

    private  VenueClient() {
    }

    public static VenueAPI getClient(final String authToken) {

        if(client != null) {
            return client;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new CustomDeserializer<List<Venue>>("venues"))
                .registerTypeAdapter(Venue.class, new CustomDeserializer<Venue>("venueDetails")).create();

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
        client = retrofit.create(VenueAPI.class);
        return client;
    }


    public interface VenueAPI {

        @GET("venues")
        Call<List<Venue>> getVenues();

        @GET("venues/{venueId}")
        Call<Venue> getVenueById(@Path("venueId") String id);

        @POST("venues")
        Call<Venue> createVenue(@Body Venue params);
    }
}
