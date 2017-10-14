package com.srmuniv.srmvenuemanagementtool.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eesh on 9/28/17.
 */

public class Reservation {


    String venueId;

    String venue;

    String occasion;

    Date startTime;
    String parsedStartTime;

    Date endTime;
    String parsedEndTime;

    long duration;

    String by;

    public String getVenueId() {
        return venueId;
    }

    public String getVenue() {
        if(venue == null) {
            venue = "Mini Hall - 1";
        }
        return venue;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getParsedStartTime() {
        if(parsedStartTime == null) {
            SimpleDateFormat format = new SimpleDateFormat("MMM dd hh:mmaa", Locale.getDefault());
            parsedStartTime = format.format(getStartTime());
        }
        return parsedStartTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getParsedEndTime() {
        if(parsedEndTime == null) {
            SimpleDateFormat format = new SimpleDateFormat("MMM dd hh:mmaa", Locale.getDefault());
            parsedEndTime = format.format(getEndTime());
        }
        return parsedEndTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Reservation(String by, String occasion, Date startTime, Date endTime, long duration) {
        this.occasion = occasion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.by = by;
        this.venueId = "1337";
    }
}
