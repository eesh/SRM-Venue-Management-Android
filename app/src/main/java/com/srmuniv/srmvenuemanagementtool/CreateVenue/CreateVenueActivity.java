package com.srmuniv.srmvenuemanagementtool.CreateVenue;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.databinding.FragmentCreateVenueBinding;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

/**
 * Created by eesh on 10/18/17.
 */

public class CreateVenueActivity extends AppCompatActivity implements CreateVenueContract.View, View.OnClickListener {

    FragmentCreateVenueBinding binding;
    CreateVenueContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_create_venue);
        VenueRepository venueRepository = VenueRepository.getInstance();
        if(venueRepository == null) {
            showMessage("You must be logged in");
            closeScreen();
            return;
        }
        binding.addVenueButton.setOnClickListener(this);
        presenter = CreateVenuePresenter.getInstance(this, VenueRepository.getInstance());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeScreen() {
        finish();
    }

    @Override
    public void setPresenter(CreateVenueContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        String name = binding.nameET.getText().toString();
        String location = binding.locationET.getText().toString();
        int capacity;
        try {
            capacity = Integer.parseInt(binding.capacityET.getText().toString());
        } catch (Exception e) {
            showMessage("Invalid capacity");
            return;
        }
        if(name.length() == 0 || location.length() == 0 || capacity < 0) {
            showMessage("Enter venue details");
            return;
        }
        Venue venue = new Venue(capacity, name, location);
        presenter.createVenue(venue);
    }
}
