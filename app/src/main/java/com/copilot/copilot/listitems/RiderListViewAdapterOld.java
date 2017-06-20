package com.copilot.copilot.listitems;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.copilot.copilot.R;
import com.copilot.copilot.listitems.RiderListItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RiderListViewAdapterOld extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<RiderListItem> riderList = null;
    private ArrayList<RiderListItem> arr;
    private int lastStartingHour = -1;
    private int lastStartingMinute = -1;
    private int lastEndingHour = -1;
    private int lastEndingMinute = -1;
    private String lastLocation = null;
    private Calendar lastDate = null;
    private float lastRating = 0f;


    public RiderListViewAdapterOld(Context context, List<RiderListItem> riderList, int startingMinute, int startingHour, int endingMinute, int endingHour, Calendar userDate, String destination) {
        mContext = context;
        this.riderList = riderList;
        lastStartingHour = startingHour;
        lastStartingMinute = startingMinute;
        lastEndingHour = endingHour;
        lastEndingMinute = endingMinute;
        lastLocation = destination;
        lastDate = userDate;
        inflater = LayoutInflater.from(mContext);
        this.arr = new ArrayList<RiderListItem>();
        this.arr.addAll(riderList);

        filter();
    }

    // contains the views that link to this class for data
    public class ViewHolder {
        TextView name;
        TextView destination;
        TextView rating;
        TextView date;
        ImageView image;
    }

    @Override
    public int getCount() {
        return riderList.size();
    }

    @Override
    public RiderListItem getItem(int position) {
        return riderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.rider_listview_item, null);
            holder.name = (TextView) view.findViewById(R.id.riderName);
            holder.destination = (TextView) view.findViewById(R.id.riderDestination);
            holder.rating = (TextView) view.findViewById(R.id.riderRating);
            holder.date = (TextView) view.findViewById(R.id.riderDate);
            holder.image = (ImageView) view.findViewById(R.id.riderImage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.name.setText(riderList.get(position).getRiderName());
        holder.destination.setText(riderList.get(position).getRiderDestination());
        holder.rating.setText(Float.toString(riderList.get(position).getRiderRating()));
        Date riderDate = riderList.get(position).getRiderStartingDate();
        holder.date.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(riderDate.getYear() + 1900).append("-").append(riderDate.getMonth() + 1).append("-").append(riderDate.getDate()).append(" ")
                .append(" from ").append(riderList.get(position).getStartingHour()).append(":").append(riderList.get(position).getStartingMinute())
                .append(" - ").append(riderList.get(position).getEndingHour()).append(":").append(riderList.get(position).getEndingMinute()));

        return view;
    }

    public void filter() {
        riderList.clear();
        for (RiderListItem item : arr) {
            Calendar riderDate = Calendar.getInstance();
            riderDate.setTime(item.getRiderStartingDate());
            if (satisfyLocationFilter(item.getRiderDestination()) && satisfyDateFilter(riderDate) &&
                    satisfyTimeFilter(item.getStartingMinute(), item.getStartingHour(), item.getEndingMinute(), item.getEndingHour()) &&
                    satisfyRatingFilter(item.getRiderRating())) {
                riderList.add(item);
            }
        }

        notifyDataSetChanged();
    }

    private boolean satisfyLocationFilter(String userDestination) {
        if (this.lastLocation == null) {
            return true;
        }

        String location = this.lastLocation.toLowerCase(Locale.getDefault());
        if (location.length() == 0 || userDestination.toLowerCase(Locale.getDefault()).contains(location)) {
            return true;
        }
        return false;
    }

    public void filterByDate(Calendar cal) {
        this.lastDate = cal;
        filter();
    }

    private boolean satisfyDateFilter(Calendar cal) {
        if (this.lastDate == null) {
            return true;
        }

        if (this.lastDate.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                this.lastDate.get(Calendar.MONTH) ==  cal.get(Calendar.MONTH) &&
                this.lastDate.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }

    public void filterByTime(int beginningMinute, int beginningHour, int endingMinute, int endingHour) {
        // This means that someone changed the time in the ending section.
        if (beginningHour != -1) {
            lastStartingMinute = beginningMinute;
            lastStartingHour = beginningHour;
        }

        if (endingHour != -1){
            lastEndingMinute = endingMinute;
            lastEndingHour = endingHour;
        }

        filter();
    }

    private boolean satisfyTimeFilter(int beginningMinute, int beginningHour, int endingMinute, int endingHour) {
        Log.v("TEST", "beginningminute: " + Integer.toString(beginningMinute));
        Log.v("TEST", "beginninghour: " + Integer.toString(beginningHour));
        Log.v("TEST", "endingminute: " + Integer.toString(endingMinute));
        Log.v("TEST", "endinghour: " + Integer.toString(endingHour));
        if (lastStartingMinute == -1 && lastStartingHour == -1 && lastEndingMinute == -1 && lastEndingHour == -1) {
            return true;
        }

        if (lastStartingHour == -1) {
            if (endingHour == lastEndingHour && endingMinute < lastEndingMinute || endingHour < lastEndingHour) {
                return true;
            }
        } else {
            if (lastEndingHour == -1) {
                if (beginningHour == lastStartingHour && beginningMinute > lastStartingMinute || beginningHour > lastStartingHour) {
                    return true;
                }
            } else {
                // If the filter is not valid then we should just return false.
                if (!checkValidTimeRange(lastStartingMinute, lastStartingHour, lastEndingMinute, lastEndingHour)) {
                    return false;
                }
                Log.v("TEST", "DID WE MAKE IT THROUGH THE DAMN VALID TIME RANGE");
                Log.v("TEST", "laststartingminute: " + Integer.toString(lastStartingMinute));
                Log.v("TEST", "lastStartinghour: " + Integer.toString(lastStartingHour));
                Log.v("TEST", "lastendingminute: " + Integer.toString(lastEndingMinute));
                Log.v("TEST", "lastendinghour: " + Integer.toString(lastEndingHour));

                if (beginningHour > lastStartingHour && endingHour < lastEndingHour || beginningHour == lastStartingHour && beginningMinute > lastStartingMinute &&
                        (endingHour < lastEndingHour || endingHour == lastEndingHour && endingMinute < lastEndingMinute)) {
//                Log.v("TEST", "DID WE MAKE IT THROUGH THE DAMN VALID TIME RANGE");
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//                    String filterStartingTime = new StringBuilder()
//                            .append(lastStartingHour).append(":").append(lastStartingMinute).toString();
//                    String filterEndingTime = new StringBuilder()
//                            .append(lastEndingHour).append(":").append(lastEndingMinute).toString();
//
//                    String userStartingTime = new StringBuilder()
//                            .append(beginningHour).append(":").append(beginningMinute).toString();
//
//                    String userEndingTime = new StringBuilder()
//                            .append(endingHour).append(":").append(endingMinute).toString();
//
//                    Date filterStartingDate = sdf.parse(filterStartingTime);
//                    Date filterEndingDate = sdf.parse(filterEndingTime);
//                    Date userStartingDate = sdf.parse(userStartingTime);
//                    Date userEndingDate = sdf.parse(userEndingTime);
//
//                    Log.v("TEST", "filterstartingdate: " + filterStartingDate.toString());
//                    Log.v("TEST", "filterendingdate: " + filterEndingDate.toString());
//                    Log.v("TEST", "userstartingdate: " + userStartingDate.toString());
//                    Log.v("TEST", "userendingdate: " + userEndingDate.toString());
//                    Calendar filterStartingCalendar = Calendar.getInstance();
//                    filterStartingCalendar.setTime(filterStartingDate);
//
//                    Calendar filterEndingCalendar = Calendar.getInstance();
//                    filterEndingCalendar.setTime(filterEndingDate);
//
//                    if (userStartingDate.after(filterStartingDate) && userEndingDate.before(filterEndingDate)) {
//                        return true;
//                    }
//
//                    return false;

//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
                    return true;

                }

                return false;
            }
        }

        return false;
    }

    private boolean satisfyRatingFilter(float userRating) {
        return userRating >= this.lastRating;
    }

    private boolean checkValidTimeRange(int beginningTimeMinute, int beginningTimeHour, int endingTimeMinute, int endingTimeHour ) {
        if (endingTimeHour > beginningTimeHour || endingTimeHour == beginningTimeHour && endingTimeMinute > beginningTimeMinute) {
            return true;
        } else {
            return false;
        }
    }
}