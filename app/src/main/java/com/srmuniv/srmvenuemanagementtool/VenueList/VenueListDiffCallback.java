package com.srmuniv.srmvenuemanagementtool.VenueList;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.srmuniv.srmvenuemanagementtool.models.Venue;

import java.util.List;

/**
 * Created by eesh on 10/18/17.
 */

public class VenueListDiffCallback extends DiffUtil.Callback {

    private final List<Venue> oldVenueList;
    private final List<Venue> newVenueList;

    public VenueListDiffCallback(List<Venue> oldVenueList, List<Venue> newVenueList) {
        this.oldVenueList = oldVenueList;
        this.newVenueList = newVenueList;
    }

    @Override
    public int getOldListSize() {
        return oldVenueList.size();
    }

    @Override
    public int getNewListSize() {
        return newVenueList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newVenueList.get(newItemPosition).getId().equals(oldVenueList.get(oldItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Venue oldVenue = oldVenueList.get(oldItemPosition);
        final Venue newVenue = newVenueList.get(newItemPosition);
        return (oldVenue.getName().equals(newVenue.getName()) == true && oldVenue.getLocation().equals(newVenue.getName()) == true);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
