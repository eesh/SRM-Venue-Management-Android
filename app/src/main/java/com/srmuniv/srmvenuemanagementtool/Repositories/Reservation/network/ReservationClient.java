package com.srmuniv.srmvenuemanagementtool.Repositories.Reservation.network;

import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.List;

import okhttp3.OkHttpClient;
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
    static String API_BASE_URL = "https://eesh.me:6767/";

    public static ReservationAPI getClient() {
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
        client = retrofit.create(ReservationAPI.class);
        return client;
    }

    interface ReservationAPI {

        @GET("reservations")
        Call<List<Reservation>> getAllReservations();

        @POST("reservation")
        Call<Reservation> createReservation(@Body Reservation reservation);

        @POST("reservation/cancel")
        Call<Reservation> cancelReservation(@Body String reservationId);

        @POST("reservation/edit")
        Call<Reservation> editReservation(@Body Reservation reservation);
    }
}
