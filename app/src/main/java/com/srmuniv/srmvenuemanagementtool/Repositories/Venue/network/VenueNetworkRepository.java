package com.srmuniv.srmvenuemanagementtool.Repositories.Venue.network;

import android.support.annotation.NonNull;

import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by eesh on 10/14/17.
 */

public class VenueNetworkRepository implements VenueDataSource {

    private VenueClient.VenueAPI client;

    private VenueNetworkRepository() {
        client = VenueClient.getClient();
    }

    public static VenueNetworkRepository getInstance() {
        return new VenueNetworkRepository();
    }

    @Override
    public void getVenues(@NonNull final LoadVenuesCallback callback) {
        Call<List<Venue>> call = client.getVenues();
        call.enqueue(new Callback<List<Venue>>() {
            @Override
            public void onResponse(Call<List<Venue>> call, Response<List<Venue>> response) {
                if(response.isSuccessful()) {
                    callback.onVenueLoaded(response.body());
                } else callback.onDataNotAvailable();
            }

            @Override
            public void onFailure(Call<List<Venue>> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getVenueById(@NonNull String id, @NonNull GetVenuesCallback callback) {

    }

    @Override
    public void createVenue(Venue venue, @NonNull final CreateVenueCallback callback) {
        Call<Venue> call = client.createVenue(venue);
        call.enqueue(new Callback<Venue>() {
            @Override
            public void onResponse(Call<Venue> call, Response<Venue> response) {
                if(response.isSuccessful()) {
                    callback.onVenueCreated(response.body());
                } else callback.onCreateFail();
            }

            @Override
            public void onFailure(Call<Venue> call, Throwable t) {
                callback.onCreateFail();
            }
        });
    }
}
