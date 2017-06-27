package com.copilot.copilot.listitems;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kjiang on 2017-06-20.
 */

public class TripListItem {
    private String driverName;

    private String pickup;
    private String destination;

    private Date tripDate;
    private int pickupHour;

    private int pickupMinute;

    private String pickupTimeStr;

    public TripListItem(String driverName, String pickup, String destination, Date tripDate, String tripTime) {
        this.driverName = driverName;
        this.pickup = pickup;
        this.destination = destination;
        this.tripDate = tripDate;
        this.pickupTimeStr = tripTime;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date d1 = (java.util.Date) sdf.parse(tripTime);
            this.pickupHour = d1.getHours();
            this.pickupMinute = d1.getMinutes();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public int getPickupHour() {
        return pickupHour;
    }

    public int getPickupMinute() {
        return pickupMinute;
    }

    public String getPickupTimeStr() {
        return pickupTimeStr;
    }

}
