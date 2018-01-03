package com.srmuniv.srmvenuemanagementtool.reservationdetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.databinding.ActivityReservationDetailsBinding;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.User;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueRepository;

import java.util.Date;

/**
 * Created by eesh on 10/29/17.
 */

public class ReservationDetailsActivity extends AppCompatActivity implements ReservationDetailsContract.View, View.OnClickListener {


    ActivityReservationDetailsBinding binding;
    ReservationDetailsContract.Presenter presenter;
    Reservation reservation;

    boolean firstStart = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_details);
        Intent intent = getIntent();
        String reservationId = intent.getStringExtra("reservationId");
        String venueId = intent.getStringExtra("venueId");
        String occasion = intent.getStringExtra("occasion");
        long startTime = intent.getLongExtra("startTime", 0);
        long endTime = intent.getLongExtra("endTime", 0);
        int duration = intent.getIntExtra("duration", 0);
        int capacity = intent.getIntExtra("capacity", 0);
        String userId = intent.getStringExtra("userId");
        String userName = intent.getStringExtra("userName");
        String userDepartment = intent.getStringExtra("userDepartment");
        User user = new User(userId, userName, userDepartment);
        reservation = new Reservation(venueId, occasion, new Date(startTime), new Date(endTime), capacity, duration, user);
        reservation.setReservationId(reservationId);
        binding.cancelReservationButton.setOnClickListener(this);
        new ReservationDetailsPresenter(this, VenueRepository.getInstance(), ReservationRepository.getInstance(), UserRepository.getInstance());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(firstStart) {
            presenter.start();
            presenter.setReservation(reservation);
            firstStart = false;
        }
    }

    @Override
    public void setPresenter(ReservationDetailsContract.Presenter presenter) {
        this.presenter = presenter;
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
    public void setReservationDetails(Reservation reservation) {
        if(reservation == null) {
            return;
        }
        binding.occasionTV.setText(reservation.getOccasion());
        binding.dateTV.setText(reservation.getStartDate());
        binding.startTimeTV.setText(reservation.getParsedStartTime());
        binding.endTimeTV.setText(reservation.getParsedEndTime());
        binding.durationTV.setText(Integer.toString(reservation.getDuration()));
        binding.capacityTV.setText(Integer.toString(reservation.getCapacity()));
    }

    @Override
    public void setVenue(String venue) {
        binding.venueTV.setText(venue);
    }

    @Override
    public void showAdminOptions() {
        hideOptions();
        binding.adminButtonsLL.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCancelOption() {
        hideOptions();
        binding.ownerButtonsLL.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCloseOption() {
        hideOptions();
        binding.userButtonsLL.setVisibility(View.VISIBLE);
    }

    public void hideOptions() {
        binding.adminButtonsLL.setVisibility(View.GONE);
        binding.userButtonsLL.setVisibility(View.GONE);
        binding.ownerButtonsLL.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.cancelReservationButton.getId()) {
            presenter.cancelReservation();
        } else if (view.getId() == binding.confirmReservationButton.getId()) {
            presenter.confirmReservation();
        } else if (view.getId() == binding.rejectReservationButton.getId()) {
            presenter.rejectReservation();
        } else if (view.getId() == binding.closeReservationButton.getId()) {
            finish();
        }
    }
}
