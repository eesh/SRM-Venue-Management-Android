package com.srmuniv.srmvenuemanagementtool.ReservationsList;

import android.util.Log;

import com.srmuniv.srmvenuemanagementtool.Repositories.Reservation.ReservationDataSource;
import com.srmuniv.srmvenuemanagementtool.Repositories.Reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/17/17.
 */

public class ReservationsListPresenter implements ReservationListContract.Presenter {

    ReservationListContract.View view;
    ReservationRepository reservationRepository;
    VenueRepository venueRepository;

    public ReservationsListPresenter(ReservationRepository reservationRepository, VenueRepository venueRepository, ReservationListContract.View view) {
        this.reservationRepository = reservationRepository;
        this.venueRepository = venueRepository;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        getReservations();
    }

    @Override
    public void getReservations() {

        reservationRepository.getReservations(new ReservationDataSource.LoadReservationsCallback() {
            @Override
            public void onReservationsLoaded(List<Reservation> reservationList) {
                mapVenueIds(reservationList, new VenueMapListener() {
                    @Override
                    public void onVenuesMapped(List<Reservation> reservations) {
                        view.replaceReservations(reservations);
                        view.showReservations(true);
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                view.showMessage("Failed to load reservations");
            }
        });
    }

    private void mapVenueIds(final List<Reservation> reservations, final VenueMapListener listener){

        venueRepository.getVenues(new VenueDataSource.LoadVenuesCallback() {
            @Override
            public void onVenueLoaded(List<Venue> venueList) {
                for(Reservation reservation: reservations) {
                    for (Venue venue : venueList) {
                        if (venue.getId().equals(reservation.getVenueId())) {
                            reservation.setVenue(venue.getName());
                        }
                    }
                }
                listener.onVenuesMapped(reservations);
            }

            @Override
            public void onDataNotAvailable() {
                Log.e("Mapper", "Failed to load venues");
                listener.onVenuesMapped(reservations);
            }
        });
    }

    interface VenueMapListener {
        void onVenuesMapped(List<Reservation> reservations);
    }
}
