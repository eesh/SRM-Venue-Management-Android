package com.srmuniv.srmvenuemanagementtool.ReservationsList;

import com.srmuniv.srmvenuemanagementtool.BasePresenter;
import com.srmuniv.srmvenuemanagementtool.BaseView;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.List;

/**
 * Created by eesh on 10/17/17.
 */

public interface ReservationListContract {

    interface View extends BaseView<Presenter> {

        void showReservations(boolean show);

        void replaceReservations(List<Reservation> reservations);

        void showMessage(String message);
    }

    interface Presenter extends BasePresenter {

        void getReservations();
    }
}
