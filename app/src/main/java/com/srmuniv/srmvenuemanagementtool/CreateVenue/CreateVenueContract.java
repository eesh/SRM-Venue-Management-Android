package com.srmuniv.srmvenuemanagementtool.CreateVenue;

import com.srmuniv.srmvenuemanagementtool.BasePresenter;
import com.srmuniv.srmvenuemanagementtool.BaseView;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

/**
 * Created by eesh on 10/18/17.
 */

public interface CreateVenueContract {

    interface View extends BaseView<Presenter> {

        void showMessage(String s);

        void closeScreen();
    }

    interface Presenter extends BasePresenter {

        void createVenue(Venue venue);
    }
}
