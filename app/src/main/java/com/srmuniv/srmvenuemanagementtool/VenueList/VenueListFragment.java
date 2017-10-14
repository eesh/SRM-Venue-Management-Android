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
import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.databinding.FragmentVenuesListBinding;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eesh on 9/28/17.
 */

public class VenueListFragment extends Fragment {

    FragmentVenuesListBinding binding;
    VenueItemClickListener venueItemClickListener;
    VenueListAdapter adapter;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Venue> venueList = new ArrayList<>();
        venueList.add(new Venue(200, "Mini Hall - 1", "TP Ganesan Auditorium"));
        venueList.add(new Venue(200, "Mini Hall - 2", "TP Ganesan Auditorium"));
        venueList.add(new Venue(3000, "TP Ganesan Auditorium", "Beside Technology Park"));
        venueList.add(new Venue(100, "ESB Seminar Hall", "Main Campus"));
        adapter = new VenueListAdapter(venueList, venueItemClickListener);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }
}
