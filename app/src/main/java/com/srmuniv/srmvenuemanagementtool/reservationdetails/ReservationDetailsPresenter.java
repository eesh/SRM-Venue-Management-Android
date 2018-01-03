package com.srmuniv.srmvenuemanagementtool.reservationdetails;

import android.util.Log;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.User;
import com.srmuniv.srmvenuemanagementtool.models.Venue;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueRepository;

import java.util.List;

/**
 * Created by eesh on 10/29/17.
 */

public class ReservationDetailsPresenter implements ReservationDetailsContract.Presenter {

    ReservationDetailsContract.View view;
    VenueRepository venueRepository;
    ReservationRepository reservationRepository;
    UserRepository  userRepository;
    Reservation reservation;
    String userRole = null;
    String userId = null;

    public ReservationDetailsPresenter(ReservationDetailsContract.View view, VenueRepository venueRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.view = view;
        this.venueRepository = venueRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        Log.e("Presenter", "fetching user information");
        userRepository.getUser(new UserDataSource.GetUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                Log.e("Presenter", "User fetched");
                userRole = user.getRole();
                userId = user.getId();
                showButtons();
            }

            @Override
            public void onDataNotAvailable() {
                Log.e("getUser", "Failed to get user details (rdp)");
            }
        });
    }


    @Override
    public void setReservation(final Reservation reservation) {
        Log.e("Presenter", "Setting reservation");
        this.reservation = reservation;
        Log.e("Presenter", reservation.getUser().getDepartment());
        Log.e(getClass().getSimpleName(), reservation.getVenueId());
        venueRepository.getVenueById(reservation.getVenueId(), new VenueDataSource.GetVenuesCallback() {
            @Override
            public void onVenueLoaded(Venue venue) {
                view.setReservationDetails(reservation);
                view.setVenue(venue.toString());
            }

            @Override
            public void onDataNotAvailable() {
                view.showMessage("Failed to load venue name");
            }
        });
        showButtons();
    }

    @Override
    public void cancelReservation() {
        reservationRepository.cancelReservation(reservation.getReservationId(), new ReservationDataSource.GetReservationCallback() {
            @Override
            public void onReservationloaded(Reservation reservation) {
                view.showMessage("Reservation cancelled");
                view.closeScreen();
            }

            @Override
            public void onDataNotAvailable() {
                view.showMessage("Failed to cancel reservation");
                view.closeScreen();
            }
        });
    }

    @Override
    public void confirmReservation() {
        if (userRepository.isAdmin()) {
            reservationRepository.confirmReservation(reservation, new ReservationDataSource.GetActionConfirmationCallback() {
                @Override
                public void onActionPerformed() {
                    view.showMessage("Reservation confirmed");
                    view.closeScreen();
                }

                @Override
                public void onActionFailed() {
                    view.showMessage("failed to confirm reservation. Please try again later");
                }
            });
        } else {
            view.showMessage("You're not authorized to perform this action");
        }
    }

    @Override
    public void rejectReservation() {
        if (userRepository.isAdmin()) {
            reservationRepository.rejectReservation(reservation, new ReservationDataSource.GetActionConfirmationCallback() {
                @Override
                public void onActionPerformed() {
                    view.showMessage("Reservation rejected");
                    view.closeScreen();
                }

                @Override
                public void onActionFailed() {
                    view.showMessage("failed to reject reservation. Please try again later");
                }
            });
        } else {
            view.showMessage("You're not authorized to perform this action");
        }
    }

    void showButtons() {
        if(userRole == null || userId == null) {
            view.showCloseOption();
        } else {
            if (userRole.equals("admin")) {
                view.showAdminOptions();
            } else {
                if (reservation.getUser().getId().equals(userId)) {
                    view.showCancelOption();
                } else view.showCloseOption();
            }
        }
    }
}
