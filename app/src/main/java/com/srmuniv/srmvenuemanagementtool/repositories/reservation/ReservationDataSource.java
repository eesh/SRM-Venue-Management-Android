package com.srmuniv.srmvenuemanagementtool.repositories.reservation;

import android.support.annotation.NonNull;

import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.List;

/**
 * Created by eesh on 10/14/17.
 */

public interface ReservationDataSource {

    interface LoadReservationsCallback {

        void onReservationsLoaded(List<Reservation> reservationList);

        void onDataNotAvailable();
    }

    interface GetReservationCallback {

        void onReservationloaded(Reservation reservation);

        void onDataNotAvailable();
    }

    void createReservation(@NonNull  Reservation reservation, @NonNull GetReservationCallback callback);

    void getReservations(@NonNull LoadReservationsCallback callback);

    void cancelReservation(@NonNull String reservationId, GetReservationCallback callback);

    void editReservation(@NonNull Reservation reservation, GetReservationCallback callback);
}