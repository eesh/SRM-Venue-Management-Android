package com.srmuniv.srmvenuemanagementtool;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.srmuniv.srmvenuemanagementtool.ReservationsList.ReservationListFragment;
import com.srmuniv.srmvenuemanagementtool.VenueList.VenueListFragment;
import com.srmuniv.srmvenuemanagementtool.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eesh on 9/29/17.
 */

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        adapter.addFragment(new VenueListFragment());
        adapter.addFragment(new ReservationListFragment());
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