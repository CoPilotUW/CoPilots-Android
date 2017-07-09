package com.copilot.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
}
