package com.srmuniv.srmvenuemanagementtool.repositories.reservation.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.srmuniv.srmvenuemanagementtool.network.ReservationClient;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationDataSource;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eesh on 10/14/17.
 */

public class ReservationNetworkRespository implements ReservationDataSource {

    private static ReservationNetworkRespository instance;
    private ReservationClient.ReservationAPI client;

    private ReservationNetworkRespository(String authToken) {
        client = ReservationClient.getClient(authToken);
    }

    public static ReservationNetworkRespository getInstance(String authToken) {
        if(instance == null) {
            instance = new ReservationNetworkRespository(authToken);
        }
        return instance;
    }

    @Override
    public void createReservation(@NonNull final Reservation reservation, @NonNull final GetReservationCallback callback) {
        Call<Reservation> call = client.createReservation(reservation);
        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if(response.isSuccessful()) {
                    callback.onReservationloaded(response.body());
                } else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                Log.e("createReservation", t.getMessage());
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getReservations(@NonNull final LoadReservationsCallback callback) {
        Call<List<Reservation>> call = client.getAllReservations();
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if(response.isSuccessful()) {
                    callback.onReservationsLoaded(response.body());
                } else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void cancelReservation(@NonNull String reservationId, final GetReservationCallback callback) {
        Call<Reservation> call = client.cancelReservation(reservationId);
        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if(response.isSuccessful()) {
                    callback.onReservationloaded(response.body());
                } else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void editReservation(@NonNull Reservation reservation, final GetReservationCallback callback) {
        Call<Reservation> call = client.editReservation(reservation);
        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if(response.isSuccessful()) {
                    callback.onReservationloaded(response.body());
                } else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }
}
