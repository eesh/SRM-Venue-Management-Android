package com.srmuniv.srmvenuemanagementtool.VenueList;

import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.srmuniv.srmvenuemanagementtool.R;
import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by eesh on 9/28/17.
 */

public class VenueListAdapter extends RecyclerView.Adapter<VenueListAdapter.VenueViewHolder> {

    List<Venue> venueList;
    VenueItemClickListener venueItemClickListener;

    public VenueListAdapter(List<Venue> venueList, VenueItemClickListener listener) {
        this.venueList = venueList;
        this.venueItemClickListener = listener;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_list_item, parent, false);
        return new VenueViewHolder(itemView, venueItemClickListener);
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, int position) {
        Venue venue = venueList.get(position);
        holder.nameTV.setText(venue.getName());
        holder.capacityTV.setText(venue.getCapacity() + "");
        holder.locationTV.setText(venue.getLocation());
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public Venue getItem(int position) {
        return venueList.get(position);
    }

    public void replaceItems(List<Venue> venueList) {
        this.venueList = venueList;
        notifyDataSetChanged();
    }

    public void updateItems(List<Venue> venueList) {
        final VenueListDiffCallback diffCallback = new VenueListDiffCallback(this.venueList, venueList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.venueList = venueList;
        diffResult.dispatchUpdatesTo(this);
    }

    class VenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView capacityTV, nameTV, locationTV;
        Button viewButton, reserveButton;
        private WeakReference<VenueItemClickListener> listenerRef;

        public VenueViewHolder(View itemView, final VenueItemClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            capacityTV = itemView.findViewById(R.id.capacityTV);
            nameTV = itemView.findViewById(R.id.nameTV);
            locationTV = itemView.findViewById(R.id.locationTV);
            viewButton = itemView.findViewById(R.id.viewButton);
            reserveButton = itemView.findViewById(R.id.bookButton);

            viewButton.setOnClickListener(this);
            reserveButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == viewButton.getId()) {
                listenerRef.get().onViewClicked(getAdapterPosition());
            } else if(view.getId() == reserveButton.getId()) {
                listenerRef.get().onReserveClicked(getAdapterPosition());
            }
        }
    }
}
