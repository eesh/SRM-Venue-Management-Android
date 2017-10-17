package com.srmuniv.srmvenuemanagementtool.VenueList;

import com.srmuniv.srmvenuemanagementtool.BasePresenter;
import com.srmuniv.srmvenuemanagementtool.BaseView;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/14/17.
 */

public interface VenueContract {

    interface View extends BaseView<Presenter> {

        void showVenues(List<Venue> venueList);

        void showMessage(String s);
    }

    interface Presenter extends BasePresenter {

        void loadVenues();
    }
}
