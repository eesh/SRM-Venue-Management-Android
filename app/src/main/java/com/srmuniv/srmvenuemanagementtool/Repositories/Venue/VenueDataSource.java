package com.srmuniv.srmvenuemanagementtool.Repositories.Venue;

import android.support.annotation.NonNull;

import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/14/17.
 */

public interface VenueDataSource {

    interface LoadVenuesCallback {

        void onVenueLoaded(List<Venue> venueList);

        void onDataNotAvailable();
    }

    interface GetVenuesCallback {

        void onVenueLoaded(Venue venue);

        void onDataNotAvailable();
    }

    interface CreateVenueCallback {

        void onVenueCreated(Venue venue);

        void onCreateFail();
    }


    void getVenues(@NonNull LoadVenuesCallback callback);

    void getVenueById(@NonNull String id, @NonNull GetVenuesCallback callback);

    void createVenue(Venue venue, @NonNull CreateVenueCallback callback);
}
