package com.srmuniv.srmvenuemanagementtool.models;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by eesh on 9/28/17.
 */

public class Venue {

    String id;

    int capacity;

    String name;

    String location;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Venue(int capacity, String name, String location) {
        this.capacity = capacity;
        this.name = name;
        this.location = location;
        this.id = ""+Calendar.getInstance().getTimeInMillis()/1000;
    }

    public String getId() {
        return id;
    }
}
