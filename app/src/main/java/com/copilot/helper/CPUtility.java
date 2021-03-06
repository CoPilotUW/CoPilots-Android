package com.copilot.helper;

import android.util.Log;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.DataFormatException;

/**
 * Created by xiaozhuoyu on 2017-07-06.
 */

public class CPUtility {
    public static String formatDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM d, ''yy");
        String formatted = format1.format(cal.getTime());

        return formatted;
    }

    public static String getDateStringForPost(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String senddate = jsonDateFormat.format(cal.getTime());

        return senddate;
    }

    // TODO: localization is going to be troublesome huh
    public static String getDateStringForTripSearch(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        Log.d("CAN YOU PLEASE COOPERATE ", year + " " + month + " " + day);

        cal.set(year, month, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);


        SimpleDateFormat tripSearchDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Log.d("I AM DONE WITH THIS", tripSearchDateFormat.format(cal.getTime()));

        return tripSearchDateFormat.format(cal.getTime());
//        return tripSearchDateFormat.format(d);
    }

    public static String getDateTimeString(String date, String time) {
        Log.d("ASDASD", date);
        Log.d("assd", time);
        String returnString = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat returnFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        try {
            Date parsedDate = jsonDateFormat.parse(date);
            Log.d("ASDASD", "parsedDate first: " + parsedDate.toString());
            Date parseTime = timeFormat.parse(time);
            Log.d("ASDASD", "parsedTime: " + parseTime.toString());
            parsedDate.setSeconds(parseTime.getSeconds());
            parsedDate.setMinutes(parseTime.getMinutes());
            parsedDate.setHours(parseTime.getHours());
            cal.setTime(parsedDate);
            returnString = returnFormat.format(cal.getTime());
        } catch (ParseException e) {
            Log.d("ASDASD", e.toString());
        }

        return returnString;
    }

    public static String convertServerDateString(String serverStr) {
        String returnString = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat returnFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        try {
            Date parsedDate = jsonDateFormat.parse(serverStr);
            cal.setTime(parsedDate);
            returnString = returnFormat.format(cal.getTime());
        } catch (ParseException e) {

        }
        return returnString;
    }

}
