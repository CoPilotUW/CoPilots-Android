package com.copilot.copilot.listitems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kjiang on 2017-06-20.
 */

public class TripListItem {
    private String tripID;
    private String driverID;
    private String driverName;

    private String pickup;
    private String destination;

    private Date tripDate;

    public TripListItem(String tripID, String driverID, String driverName, String pickup, String destination, Date tripDate) {
        this.tripID = tripID;
        this.driverID = driverID;
        this.driverName = driverName;
        this.pickup = pickup;
        this.destination = destination;
        this.tripDate = tripDate;
    }

    public String getTripID() {
        return tripID;
    }

    public String getDriverID() {
        return driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPickup() {
        return pickup;
    }

    public String getDestination() {
        return destination;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public String getPickupTimeStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.tripDate);
        return calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
    }

}
