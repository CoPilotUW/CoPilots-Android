package com.copilot.copilot.listitems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaozhuoyu on 2017-05-18.
 */
public class RiderListItem {
    private String riderName;
    private String destination;
    private float riderRating;
    private Date startingDate;
    private int startingHour;
    private int startingMinute;
    private int endingHour;
    private int endingMinute;

    public RiderListItem(String name, String destination, float rating, Date date, String startingTime, String endingTime) {
        this.riderName = name;
        this.destination = destination;
        this.riderRating = rating;
        this.startingDate = date;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date d1 = (java.util.Date) sdf.parse(startingTime);
            this.startingHour = d1.getHours();
            this.startingMinute = d1.getMinutes();
            Date d2 = (java.util.Date)sdf.parse(endingTime);
            this.endingHour = d2.getHours();
            this.endingMinute = d2.getMinutes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRiderName() {
        return this.riderName;
    }

    public String getRiderDestination() {
        return this.destination;
    }

    public float getRiderRating() {
        return this.riderRating;
    }

    public Date getRiderStartingDate() {
        return this.startingDate;
    }

    public int getStartingHour() {
        return startingHour;
    }

    public int getStartingMinute() {
        return startingMinute;
    }

    public int getEndingHour() {
        return endingHour;
    }

    public int getEndingMinute() {
        return endingMinute;
    }
}
