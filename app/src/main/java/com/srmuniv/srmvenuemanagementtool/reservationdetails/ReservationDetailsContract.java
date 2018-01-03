package com.srmuniv.srmvenuemanagementtool.reservationdetails;

import com.srmuniv.srmvenuemanagementtool.BasePresenter;
import com.srmuniv.srmvenuemanagementtool.BaseView;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

/**
 * Created by eesh on 10/29/17.
 */

public interface ReservationDetailsContract {

    interface View extends BaseView<Presenter> {
        void showMessage(String message);

        void closeScreen();

        void setReservationDetails(Reservation reservation);

        void setVenue(String venue);

        void showAdminOptions();

        void showCancelOption();

        void showCloseOption();
    }

    interface Presenter extends BasePresenter {
        void setReservation(Reservation reservation);

        void cancelReservation();

        void confirmReservation();

        void rejectReservation();
    }
}
