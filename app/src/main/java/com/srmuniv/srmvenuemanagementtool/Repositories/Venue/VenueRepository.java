package com.srmuniv.srmvenuemanagementtool.Repositories.Venue;

import android.support.annotation.NonNull;

import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.local.VenueLocalRepository;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.network.VenueNetworkRepository;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 9/29/17.
 */

public class VenueRepository implements VenueDataSource {

    private static VenueRepository repository;
    private VenueDataSource venueLocalRepository;
    private VenueDataSource venueNetworkRepository;
    private List<Venue> venueList;

    private VenueRepository() {
    }

    public static VenueRepository getInstance() {
        if(repository == null) {
            repository = new VenueRepository();
            repository.venueNetworkRepository = VenueNetworkRepository.getInstance();
            repository.venueLocalRepository = VenueLocalRepository.getInstance();
        }
        return repository;
    }

    @Override
    public void getVenues(@NonNull final LoadVenuesCallback callback) {
        if(venueList != null) {
            callback.onVenueLoaded(venueList);
            return;
        }
        venueNetworkRepository.getVenues(new LoadVenuesCallback() {
            @Override
            public void onVenueLoaded(List<Venue> venueList) {
                repository.venueList = venueList;
                callback.onVenueLoaded(venueList);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getVenueById(@NonNull String id, @NonNull GetVenuesCallback callback) {
        if(venueList != null) {
            for(Venue venue:venueList) {
                if(venue.getId().equals(id)) {
                    callback.onVenueLoaded(venue);
                    return;
                }
            }
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void createVenue(Venue venue, @NonNull final CreateVenueCallback callback) {
        venueNetworkRepository.createVenue(venue, new CreateVenueCallback() {
            @Override
            public void onVenueCreated(Venue venue) {
                callback.onVenueCreated(venue);
                venueList.add(venue);
            }

            @Override
            public void onCreateFail() {
                callback.onCreateFail();
            }
        });
    }
}
