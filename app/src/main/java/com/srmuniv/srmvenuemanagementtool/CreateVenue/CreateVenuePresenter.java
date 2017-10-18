package com.srmuniv.srmvenuemanagementtool.CreateVenue;

import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueDataSource;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

/**
 * Created by eesh on 10/18/17.
 */

public class CreateVenuePresenter implements CreateVenueContract.Presenter {

    private CreateVenueContract.View view;
    private VenueRepository repository;
    private static CreateVenuePresenter instance;

    public CreateVenuePresenter(CreateVenueContract.View view, VenueRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public static CreateVenuePresenter getInstance(CreateVenueContract.View view, VenueRepository repository) {
        if(instance == null) {
            instance = new CreateVenuePresenter(view, repository);
        }
        return instance;
    }

    @Override
    public void start() {

    }

    @Override
    public void createVenue(Venue venue) {
        repository.createVenue(venue, new VenueDataSource.CreateVenueCallback() {
            @Override
            public void onVenueCreated(Venue venue) {
                view.showMessage("Venue added successfully");
                view.closeScreen();
            }

            @Override
            public void onCreateFail() {
                view.showMessage("Failed to add new venue");
            }
        });
    }
}
