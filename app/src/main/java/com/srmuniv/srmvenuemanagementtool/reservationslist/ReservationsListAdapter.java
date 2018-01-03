package com.srmuniv.srmvenuemanagementtool.reservationslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.models.Reservation;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by eesh on 9/28/17.
 */

public class ReservationsListAdapter extends RecyclerView.Adapter<ReservationsListAdapter.ReservationViewHolder> {

    List<Reservation> reservationList;
    ReservationItemClickListener clickListener;

    public ReservationsListAdapter(List<Reservation> reservationList, ReservationItemClickListener listener) {
        this.reservationList = reservationList;
        this.clickListener = listener;
    }

    public void setData(List<Reservation> reservations) {
        this.reservationList = reservations;
        notifyDataSetChanged();
    }

    public Reservation getItem(int position) {
        return reservationList.get(position);
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_list_item, parent, false);
        return new ReservationViewHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        Reservation reservation = reservationList.get(position);
        holder.departmentTV.setText(reservation.getUser().getDepartment());
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

    class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView occasionTV, departmentTV, venueTV;
        TextView startTimeTV, endTimeTV, durationTV;
        private WeakReference<ReservationItemClickListener> listenerWeakReference;

        public ReservationViewHolder(View itemView, ReservationItemClickListener listener) {
            super(itemView);
            occasionTV = itemView.findViewById(R.id.occasionTV);
            departmentTV = itemView.findViewById(R.id.departmentTV);
            venueTV = itemView.findViewById(R.id.venueTV);
            startTimeTV = itemView.findViewById(R.id.timeTV);
            endTimeTV = itemView.findViewById(R.id.endTimeTV);
            if (listener != null) {
                listenerWeakReference = new WeakReference<>(listener);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            listenerWeakReference.get().onClick(getAdapterPosition());
        }
    }
}
