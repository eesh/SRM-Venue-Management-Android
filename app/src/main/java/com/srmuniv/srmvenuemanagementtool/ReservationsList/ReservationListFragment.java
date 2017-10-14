package com.srmuniv.srmvenuemanagementtool.ReservationsList;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.VenueList.VenueListAdapter;
import com.srmuniv.srmvenuemanagementtool.databinding.FragmentReservationsListBinding;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eesh on 9/29/17.
 */

public class ReservationListFragment extends Fragment {

    FragmentReservationsListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservations_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(new Reservation("Software Engineering", "National Workshop", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 86400000));
        reservationList.add(new Reservation("Computer Science Engineering", "Placement Training", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 86400000));
        reservationList.add(new Reservation("Mechanical Engineering", "Camber Racing recruitment", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 86400000));
        reservationList.add(new Reservation("Real Estate Management", "Nobel Laureate Speech", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 86400000));
        reservationList.add(new Reservation("IT and Communications Engineering", "National Workshop", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 86400000));
        ReservationsListAdapter adapter = new ReservationsListAdapter(reservationList);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);
    }
}
