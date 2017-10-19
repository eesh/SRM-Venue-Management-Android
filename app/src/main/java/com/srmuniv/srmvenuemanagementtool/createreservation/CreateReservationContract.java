package com.srmuniv.srmvenuemanagementtool.createreservation;

import com.srmuniv.srmvenuemanagementtool.BasePresenter;
import com.srmuniv.srmvenuemanagementtool.BaseView;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/18/17.
 */

public interface CreateReservationContract {

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void closeScreen();

        void setVenues(List<Venue> venueList);
    }

    interface Presenter extends BasePresenter {

        void makeReservation(Reservation reservation);
    }
}
