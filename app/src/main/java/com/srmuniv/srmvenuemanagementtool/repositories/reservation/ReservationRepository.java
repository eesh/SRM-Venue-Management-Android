package com.srmuniv.srmvenuemanagementtool.repositories.reservation;

import android.support.annotation.NonNull;

import com.srmuniv.srmvenuemanagementtool.repositories.reservation.network.ReservationNetworkRespository;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.List;

/**
 * Created by eesh on 10/14/17.
 */

public class ReservationRepository implements ReservationDataSource {

    private static ReservationRepository instance;
    private ReservationDataSource reservationNetworkRespository;

    private ReservationRepository(ReservationNetworkRespository reservationNetworkRespository) {
        this.reservationNetworkRespository = reservationNetworkRespository;
    }

    public static ReservationRepository getInstance(ReservationNetworkRespository respository) {
        if(instance == null) {
            instance = new ReservationRepository(respository);
        }
        return instance;
    }

    @Override
    public void createReservation(@NonNull Reservation reservation, @NonNull final GetReservationCallback callback) {
        reservationNetworkRespository.createReservation(reservation, new GetReservationCallback() {
            @Override
            public void onReservationloaded(Reservation reservation) {
                callback.onReservationloaded(reservation);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getReservations(@NonNull final LoadReservationsCallback callback) {
        reservationNetworkRespository.getReservations(new LoadReservationsCallback() {
            @Override
            public void onReservationsLoaded(List<Reservation> reservationList) {

                callback.onReservationsLoaded(reservationList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void cancelReservation(@NonNull String reservationId, final GetReservationCallback callback) {
        reservationNetworkRespository.cancelReservation(reservationId, new GetReservationCallback() {
            @Override
            public void onReservationloaded(Reservation reservation) {
                callback.onReservationloaded(reservation);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void editReservation(@NonNull Reservation reservation, final GetReservationCallback callback) {
        reservationNetworkRespository.editReservation(reservation, new GetReservationCallback() {
            @Override
            public void onReservationloaded(Reservation reservation) {
                callback.onReservationloaded(reservation);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getReservationById(@NonNull String reservationId, GetReservationCallback callback) {

    }

    public static ReservationRepository getInstance() {
        return instance;
    }

    @Override
    public void confirmReservation(Reservation reservation, GetActionConfirmationCallback callback) {
        reservationNetworkRespository.confirmReservation(reservation, callback);
    }

    @Override
    public void rejectReservation(Reservation reservation, GetActionConfirmationCallback callback) {
        reservationNetworkRespository.rejectReservation(reservation, callback);
    }
}
