package com.srmuniv.srmvenuemanagementtool.venuelist;

import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/14/17.
 */

public class VenuePresenter implements VenueContract.Presenter {

    VenueContract.View view;
    VenueRepository venueDataSource;

    boolean resumed = false;

    public VenuePresenter(VenueRepository venueDataSource, VenueContract.View view) {
        this.venueDataSource = venueDataSource;
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void start() {
        if(!resumed) {
            resumed = true;
            loadVenues();
        } else {
            updateVenues();
        }
    }

    private void updateVenues() {
        venueDataSource.getVenues(new VenueDataSource.LoadVenuesCallback() {
            @Override
            public void onVenueLoaded(List<Venue> venueList) {
                view.updateVenues(venueList);
            }

            @Override
            public void onDataNotAvailable() {
                view.showMessage("No venues available");
            }
        });
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

    @Override
    public void addVenueButtonClicked() {
        view.showAddVenueScreen();
    }
}
