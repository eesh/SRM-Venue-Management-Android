package com.srmuniv.srmvenuemanagementtool;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.Repositories.Reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.Repositories.Reservation.network.ReservationNetworkRespository;
import com.srmuniv.srmvenuemanagementtool.Repositories.Venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.ReservationsList.ReservationListFragment;
import com.srmuniv.srmvenuemanagementtool.ReservationsList.ReservationsListPresenter;
import com.srmuniv.srmvenuemanagementtool.VenueList.VenueListFragment;
import com.srmuniv.srmvenuemanagementtool.VenueList.VenuePresenter;
import com.srmuniv.srmvenuemanagementtool.databinding.ActivityHomeBinding;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eesh on 9/29/17.
 */

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    VenuePresenter venuePresenter;
    ReservationsListPresenter reservationsListPresenter;
    PagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        Intent intent = getIntent();
        String authToken = intent.getStringExtra("authToken");
        if(authToken == null) {
            Toast.makeText(this, "You've been logged out", Toast.LENGTH_SHORT).show();
            finish();
        }

        adapter = new PagerAdapter(getFragmentManager());

        VenueListFragment venueListFragment = VenueListFragment.newInstance();
        venuePresenter = new VenuePresenter(VenueRepository.getInstance(authToken), venueListFragment);
        adapter.addFragment(venueListFragment);

        ReservationListFragment reservationListFragment = ReservationListFragment.getInstance();
        reservationsListPresenter = new ReservationsListPresenter(
                ReservationRepository.getInstance(ReservationNetworkRespository.getInstance(authToken)),
                reservationListFragment);
        adapter.addFragment(reservationListFragment);

        binding.viewPager.setAdapter(adapter);
    }


    class PagerAdapter extends FragmentPagerAdapter {

        List<android.app.Fragment> fragmentList;


        public PagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        public void addFragment(android.app.Fragment fragment) {
            fragmentList.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}