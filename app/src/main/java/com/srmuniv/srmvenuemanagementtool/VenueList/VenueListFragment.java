package com.srmuniv.srmvenuemanagementtool.VenueList;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.srmuniv.srmvenuemanagementtool.CreateReservation;
import com.srmuniv.srmvenuemanagementtool.CreateVenue.CreateVenueActivity;
import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.databinding.FragmentVenuesListBinding;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eesh on 9/28/17.
 */

public class VenueListFragment extends Fragment implements VenueContract.View, View.OnClickListener {

    FragmentVenuesListBinding binding;
    VenueItemClickListener venueItemClickListener;
    VenueListAdapter adapter;
    VenueContract.Presenter presenter;


    public VenueListFragment() {

    }

    public static VenueListFragment newInstance() {
        return new VenueListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venues_list, container, false);
        venueItemClickListener = new VenueItemClickListener() {
            @Override
            public void onReserveClicked(int position) {
                Venue venue = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), CreateReservation.class);
                intent.putExtra("venueName", venue.getName());
                startActivity(intent);
            }

            @Override
            public void onViewClicked(int position) {
                Venue venue = adapter.getItem(position);
                Toast.makeText(getActivity(), venue.getName(), Toast.LENGTH_SHORT).show();
            }
        };
        binding.addVenueFAB.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(VenueContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showVenues(List<Venue> venueList) {
        if(adapter == null) {
            adapter = new VenueListAdapter(venueList, venueItemClickListener);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            binding.recyclerView.setAdapter(adapter);
        } else {
            adapter.replaceItems(venueList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAddVenueScreen() {
        Intent intent = new Intent(getActivity(), CreateVenueActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateVenues(List<Venue> venueList) {
        adapter.replaceItems(venueList);
    }

    @Override
    public void onClick(View view) {
        presenter.addVenueButtonClicked();
    }
}
