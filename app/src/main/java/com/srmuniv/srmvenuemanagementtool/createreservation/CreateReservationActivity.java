package com.srmuniv.srmvenuemanagementtool.createreservation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.databinding.ActivityMakeReservationBinding;

/**
 * Created by eesh on 9/29/17.
 */

public class CreateReservationActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMakeReservationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_reservation);
        binding.makeReservationButton.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null) {
            String venueName = intent.getStringExtra("venueName");
            if(venueName != null) {
                setSpinnerItem(venueName);
            } else {
                Log.e("TAG", "Venue null");
            }
        } else {
            Log.e("TAG", "Intent null");
        }
    }

    private void setSpinnerItem(String venueName) {
        int count = binding.venueSpinner.getCount();
        for(int index = 0; index < count; index ++) {
            String itemName = (String) binding.venueSpinner.getItemAtPosition(index);
            Log.e("TAG", itemName);
            if(itemName.equals(venueName)) {
                binding.venueSpinner.setSelection(index);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "Created reservation request", Toast.LENGTH_SHORT).show();
        finish();
    }
}
