package com.srmuniv.srmvenuemanagementtool.repositories.venue;

import android.support.annotation.NonNull;
import android.util.Log;

import com.srmuniv.srmvenuemanagementtool.repositories.venue.local.VenueLocalRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.network.VenueNetworkRepository;
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

    public static VenueRepository getInstance(String authToken) {
        if(repository == null) {
            repository = new VenueRepository();
            repository.venueNetworkRepository = VenueNetworkRepository.getInstance(authToken);
            repository.venueLocalRepository = VenueLocalRepository.getInstance();
        }
        return repository;
    }

    public static VenueRepository getInstance() {
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

    public void getVenueById(final String venueId, final GetVenuesCallback callback) {
        Log.e(getClass().getSimpleName(), "List of venues:");
        if(venueList != null) {
            for (Venue venue : venueList) {
                Log.e(getClass().getSimpleName(), venue.getId());
                if (venue.getId().equals(venueId)) {
                    callback.onVenueLoaded(venue);
                    return;
                }
            }
            callback.onDataNotAvailable();
        } else {
            getVenues(new LoadVenuesCallback() {
                @Override
                public void onVenueLoaded(List<Venue> venueList) {
                    for (Venue venue : venueList) {
                        if (venue.getId().equals(venueId)) {
                            callback.onVenueLoaded(venue);
                            return;
                        }
                    }
                    callback.onDataNotAvailable();
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }
}
