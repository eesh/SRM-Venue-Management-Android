package com.srmuniv.srmvenuemanagementtool.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;
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

/**
 * Created by eesh on 10/14/17.
 */

public class ReservationClient {

    static ReservationAPI client;
    static String API_BASE_URL = "http://eesh.me:6767/";

    public static ReservationAPI getClient(final String authToken) {
        if(client != null) {
            return client;
        }

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Reservation.class, new CustomDeserializer<Reservation>("reservationDetails"))
                .registerTypeAdapter(List.class, new CustomDeserializer<List<Reservation>>("reservations")).create();

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
        client = retrofit.create(ReservationAPI.class);
        return client;
    }

    public interface ReservationAPI {

        @GET("reservation/all")
        Call<List<Reservation>> getAllReservations();

        @POST("reservation")
        Call<Reservation> createReservation(@Body Reservation reservation);

        @POST("reservation/cancel")
        Call<Reservation> cancelReservation(@Body String reservationId);

        @POST("reservation/edit")
        Call<Reservation> editReservation(@Body Reservation reservation);
    }
}
