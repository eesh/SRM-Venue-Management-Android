package com.srmuniv.srmvenuemanagementtool.models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eesh on 9/28/17.
 */

public class Reservation {

    @SerializedName("venueId")
    String venueId;

    String venue;

    @SerializedName("occasion")
    String occasion;

    @SerializedName("startTime")
    Date startTime;
    String parsedStartTime;


    @SerializedName("endTime")
    Date endTime;
    String parsedEndTime;

    @SerializedName("duration")
    int duration;

    @SerializedName("user")
    User user;

    @SerializedName("confirmed")
    boolean confirmed;

    @SerializedName("capacity")
    int capacity;


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

    public void setVenue(String venue) {
        this.venue = venue;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public User getUser() {
        return user;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Reservation(String venueId, String venue, String occasion, Date startTime, String parsedStartTime, Date endTime, String parsedEndTime, int duration, User user, boolean confirmed) {
        this.venueId = venueId;
        this.venue = venue;
        this.occasion = occasion;
        this.startTime = startTime;
        this.parsedStartTime = parsedStartTime;
        this.endTime = endTime;
        this.parsedEndTime = parsedEndTime;
        this.duration = duration;
        this.user = user;
        this.confirmed = confirmed;
    }

    public Reservation(String venueId, String occasion, Date startTime, Date endTime, int capacity) {
        this.venueId = venueId;
        this.occasion = occasion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public Reservation(String venueId, String occasion, Date startTime, Date endTime, int capacity, int duration) {
        this.venueId = venueId;
        this.occasion = occasion;
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.duration = duration;
    }
}
