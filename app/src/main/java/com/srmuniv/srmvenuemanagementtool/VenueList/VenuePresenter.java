package com.srmuniv.srmvenuemanagementtool.VenueList;

import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/14/17.
 */

public class VenuePresenter implements VenueContract.Presenter {

    VenueContract.View view;
    VenueRepository venueDataSource;

    public VenuePresenter(VenueRepository venueDataSource, VenueContract.View view) {
        this.venueDataSource = venueDataSource;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadVenues();
    }

    @Override
    public void loadVenues() {



        venueDataSource.getVenues(new VenueDataSource.LoadVenuesCallback() {
            @Override
            public void onVenueLoaded(List<Venue> venueList) {
                view.showVenues(venueList);
            }

            @Override
            public void onDataNotAvailable() {
                view.showMessage("No venues available");
            }
        });
    }
}
