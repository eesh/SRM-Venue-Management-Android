package com.srmuniv.srmvenuemanagementtool.createreservation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.databinding.ActivityMakeReservationBinding;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.Venue;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueRepository;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by eesh on 9/29/17.
 */

public class CreateReservationActivity extends AppCompatActivity implements CreateReservationContract.View, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnFocusChangeListener {

    ActivityMakeReservationBinding binding;
    CreateReservationContract.Presenter presenter;

    ArrayAdapter<Venue> adapter = null;

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    Calendar startTimeCalendar = null;
    Calendar endTimeCalendar = null;
    int duration = 0;

    boolean startTime = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_reservation);
        binding.makeReservationButton.setOnClickListener(this);
        binding.dateET.setInputType(0);
        binding.startTimeET.setInputType(0);
        binding.endTimeET.setInputType(0);
        binding.dateET.setOnFocusChangeListener(this);
        binding.startTimeET.setOnFocusChangeListener(this);
        binding.endTimeET.setOnFocusChangeListener(this);
        ReservationRepository reservationRepository = ReservationRepository.getInstance();
        if(reservationRepository == null) {
            showMessage("Log in to continue");
            finish();
            return;
        }
        VenueRepository venueRepository = VenueRepository.getInstance();
        if(venueRepository == null) {
            showMessage("Log in to continue");
            finish();
            return;
        }
        new CreateReservationPresenter(this, reservationRepository, venueRepository);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.start();
        Intent intent = getIntent();
        if(intent != null) {
            String venueName = intent.getStringExtra("venueId");
            if(venueName != null) {
                setSpinnerItem(venueName);
            } else {
                Log.e("TAG", "Venue null");
            }
        } else {
            Log.e("TAG", "Intent null");
        }
    }

    private void setSpinnerItem(String venueId) {
        int count = binding.venueSpinner.getCount();
        for(int index = 0; index < count; index ++) {
            Venue itemName = (Venue) binding.venueSpinner.getItemAtPosition(index);
            Log.e("TAG", itemName.toString());
            if(itemName.getId().equals(venueId)) {
                binding.venueSpinner.setSelection(index);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {

        String occasion = binding.occasionET.getText().toString();
        int capacity = 0;
        try {
            capacity = Integer.parseInt(binding.capacityET.getText().toString());
        } catch (Exception e) {
            showMessage("Invalid capacity");
            return;
        }
        if(occasion.length() == 0 || capacity < 0) {
            showMessage("Please fill in all the fields");
            return;
        }

        if(startTimeCalendar ==null || endTimeCalendar == null || duration <= 0) {
            showMessage("Set time and date");
            return;
        }

        Venue venue = (Venue) binding.venueSpinner.getSelectedItem();

        Reservation reservation = new Reservation(venue.getId(), occasion, startTimeCalendar.getTime(), endTimeCalendar.getTime(), capacity, duration);
        presenter.makeReservation(reservation);
    }

    @Override
    public void setPresenter(CreateReservationContract.Presenter presenter) {
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
    public void setVenues(List<Venue> venueList) {
        adapter = new ArrayAdapter<Venue>(this, android.R.layout.simple_spinner_item, venueList);
        binding.venueSpinner.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        startTimeCalendar = Calendar.getInstance();
        startTimeCalendar.set(Calendar.YEAR, year);
        startTimeCalendar.set(Calendar.MONTH, month);
        startTimeCalendar.set(Calendar.DAY_OF_MONTH, day);

        endTimeCalendar = Calendar.getInstance();
        endTimeCalendar.set(Calendar.YEAR, year);
        endTimeCalendar.set(Calendar.MONTH, month);
        endTimeCalendar.set(Calendar.DAY_OF_MONTH, day);
        
        datePickerDialog = null;
        setDate();
    }

    private void setDate() {
        String date = String.format(Locale.getDefault(), "%d %s, %d", startTimeCalendar.get(Calendar.DAY_OF_MONTH),
                startTimeCalendar.getDisplayName(Calendar.MONTH,
                        Calendar.SHORT,
                        Locale.getDefault()),
                startTimeCalendar.get(Calendar.YEAR));
        Log.e("setDate", date);
        binding.dateET.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        if(startTime) {
            if(startTimeCalendar == null) {
                startTimeCalendar = Calendar.getInstance();
            }
            Log.e("hour", ""+hour);
            startTimeCalendar.set(Calendar.HOUR_OF_DAY, hour);
            startTimeCalendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mmaa", Locale.getDefault());
            binding.startTimeET.setText(sdf.format(startTimeCalendar.getTime()));
        } else {
            if(endTimeCalendar == null) {
                endTimeCalendar = Calendar.getInstance();
            }
            endTimeCalendar.set(Calendar.HOUR_OF_DAY, hour);
            endTimeCalendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mmaa", Locale.getDefault());
            binding.endTimeET.setText(sdf.format(endTimeCalendar.getTime()));

            setDuration();
        }
        timePickerDialog = null;
    }

    private void setDuration() {
        int startHour = startTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int endHour = endTimeCalendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = startTimeCalendar.get(Calendar.MINUTE);
        int endMinute = endTimeCalendar.get(Calendar.MINUTE);

        int totalMinutes = (endHour - startHour) * 60 + (endMinute - startMinute);
        int durationInHours = totalMinutes / 60;
        int durationInMinutes = totalMinutes - (durationInHours * 60);

        duration = totalMinutes;
        binding.durationTV.setText(durationInHours +"hrs "+durationInMinutes+"mins");
    }

    @Override
    public void onFocusChange(View view, boolean focus) {
        if(focus) {
            Calendar calendar = Calendar.getInstance();
            Log.e("onFocusChange", "" + view.getId());
            if (view.getId() == binding.dateET.getId()) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.dateET.getWindowToken(), 0);
                }
                datePickerDialog = new DatePickerDialog(CreateReservationActivity.this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pick reservation date");
                datePickerDialog.show();
                datePickerDialog = null;
            } else if (view.getId() == binding.startTimeET.getId()) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.startTimeET.getWindowToken(), 0);
                }
                timePickerDialog = new TimePickerDialog(CreateReservationActivity.this, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Pick reservation time");
                startTime = true;
                timePickerDialog.show();
                timePickerDialog = null;
            } else if (view.getId() == binding.endTimeET.getId()) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.endTimeET.getWindowToken(), 0);
                }
                timePickerDialog = new TimePickerDialog(CreateReservationActivity.this, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Pick event duration");
                startTime = false;
                timePickerDialog.show();
                timePickerDialog = null;
            }
        }
    }
}
