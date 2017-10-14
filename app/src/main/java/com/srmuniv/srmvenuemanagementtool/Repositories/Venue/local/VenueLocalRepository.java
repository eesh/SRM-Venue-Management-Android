package com.srmuniv.srmvenuemanagementtool.Repositories.Venue.local;

import android.support.annotation.NonNull;

import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

/**
 * Created by eesh on 9/29/17.
 */

public class VenueLocalRepository implements VenueDataSource {

    static VenueLocalRepository instance;

    public static VenueLocalRepository getInstance() {
        if(instance == null) {
            instance = new VenueLocalRepository();
        }
        return instance;
    }

    @Override
    public void getVenues(@NonNull LoadVenuesCallback callback) {

    }

    @Override
    public void getVenueById(@NonNull String id, @NonNull GetVenuesCallback callback) {

    }

    @Override
    public void createVenue(Venue venue, @NonNull CreateVenueCallback callback) {

    }
}
