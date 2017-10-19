package com.srmuniv.srmvenuemanagementtool.createreservation;

import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.Venue;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueRepository;

import java.util.List;

/**
 * Created by eesh on 10/18/17.
 */

public class CreateReservationPresenter implements CreateReservationContract.Presenter {

    CreateReservationContract.View view;
    ReservationRepository reservationRepository;
    VenueRepository venueRepository;

    public CreateReservationPresenter(CreateReservationContract.View view, ReservationRepository reservationRepository, VenueRepository venueRepository) {
        this.view = view;
        this.reservationRepository = reservationRepository;
        this.venueRepository = venueRepository;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadVenues();
    }

    private void loadVenues() {
        venueRepository.getVenues(new VenueDataSource.LoadVenuesCallback() {
            @Override
            public void onVenueLoaded(List<Venue> venueList) {
                view.setVenues(venueList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void makeReservation(Reservation reservation) {
        reservationRepository.createReservation(reservation, new ReservationDataSource.GetReservationCallback() {
            @Override
            public void onReservationloaded(Reservation reservation) {
                view.showMessage("Venue reservation request made. Check status from homescreen");
                view.closeScreen();
            }

            @Override
            public void onDataNotAvailable() {
                view.showMessage("Failed to make reservation.");
            }
        });
    }
}
