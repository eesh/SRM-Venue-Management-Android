package com.srmuniv.srmvenuemanagementtool.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.models.User;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.ReservationRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.reservation.network.ReservationNetworkRespository;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserDataSource;
import com.srmuniv.srmvenuemanagementtool.repositories.user.UserRepository;
import com.srmuniv.srmvenuemanagementtool.repositories.venue.VenueRepository;
import com.srmuniv.srmvenuemanagementtool.reservationslist.ReservationListFragment;
import com.srmuniv.srmvenuemanagementtool.reservationslist.ReservationsListPresenter;
import com.srmuniv.srmvenuemanagementtool.venuelist.VenueListFragment;
import com.srmuniv.srmvenuemanagementtool.venuelist.VenuePresenter;
import com.srmuniv.srmvenuemanagementtool.databinding.ActivityHomeBinding;

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
        Log.e("AuthToken", authToken);

        VenueListFragment venueListFragment = VenueListFragment.newInstance();
        venuePresenter = new VenuePresenter(VenueRepository.getInstance(authToken), venueListFragment);
        adapter.addFragment(venueListFragment);

        ReservationListFragment reservationListFragment = ReservationListFragment.getInstance();
        reservationsListPresenter = new ReservationsListPresenter(
                ReservationRepository.getInstance(ReservationNetworkRespository.getInstance(authToken)),
                VenueRepository.getInstance(authToken),
                reservationListFragment);
        adapter.addFragment(reservationListFragment);

        binding.viewPager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(this.getClass().getSimpleName(), "Requesting user information on home activity");
        UserRepository.getInstance().getUser(new UserDataSource.GetUserCallback() {
            @Override
            public void onUserLoaded(User user) {
                Log.e(this.getClass().getSimpleName(), user.getRole());
                Log.e(this.getClass().getSimpleName(), "User loaded on home activity");
            }

            @Override
            public void onDataNotAvailable() {
                Log.e(this.getClass().getSimpleName(), "Failed to load user on home activity");
            }
        });
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