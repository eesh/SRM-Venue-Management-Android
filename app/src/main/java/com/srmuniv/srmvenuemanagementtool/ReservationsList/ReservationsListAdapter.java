package com.srmuniv.srmvenuemanagementtool.ReservationsList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.util.List;

/**
 * Created by eesh on 9/28/17.
 */

public class ReservationsListAdapter extends RecyclerView.Adapter<ReservationsListAdapter.ReservationViewHolder> {

    List<Reservation> reservationList;

    public ReservationsListAdapter(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_list_item, parent, false);
        return new ReservationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.departmentTV.setText(reservation.getBy());
        holder.occasionTV.setText(reservation.getOccasion());
        holder.venueTV.setText(reservation.getVenue());
        holder.startTimeTV.setText(reservation.getParsedStartTime());
        holder.endTimeTV.setText(reservation.getParsedEndTime());
//        holder.durationTV.setText(""+reservation.getDuration());
    }

    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {

        TextView occasionTV, departmentTV, venueTV;
        TextView startTimeTV, endTimeTV, durationTV;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            occasionTV = itemView.findViewById(R.id.occasionTV);
            departmentTV = itemView.findViewById(R.id.departmentTV);
            venueTV = itemView.findViewById(R.id.venueTV);
            startTimeTV = itemView.findViewById(R.id.timeTV);
            endTimeTV = itemView.findViewById(R.id.endTimeTV);
        }
    }
}
