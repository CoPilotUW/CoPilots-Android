package com.copilot.copilot.listitems;

import android.content.Context;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.copilot.copilot.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kjiang on 2017-06-20.
 */

public class TripListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;

    private List<TripListItem> tripList = new ArrayList<>();

    private String fromLocation = "";
    private String toLocation = "";

    private Calendar rideDate = null;
    private int rideHour = -1;
    private int rideMinute = -1;

    // TODO: add other filtering fields here as needed
    public TripListViewAdapter(
        Context context,
        List<TripListItem> tripList,
        String fromLocation,
        String toLocation,
        Calendar rideDate,
        int rideHour,
        int rideMinute
    ) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(this.mContext);

        this.tripList.addAll(tripList);

        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.rideDate = rideDate;
        this.rideHour = rideHour;
        this.rideMinute = rideMinute;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView pickupView;
        TextView destinationView;
        TextView dateView;
    }

    @Override
    public int getCount() {
        return tripList.size();
    }

    @Override
    public TripListItem getItem(int position) {
        return tripList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO: actually get something like an ID
        return position;
    }

    // for each trip stored in the trip list (model)
    // map trip (model) fields to fields needed by the rendered Android component/resource
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(R.layout.trip_screen_trip, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.riderImage);

            viewHolder.nameView = (TextView) view.findViewById(R.id.driverName);
            viewHolder.pickupView = (TextView) view.findViewById(R.id.pickup);
            viewHolder.destinationView = (TextView) view.findViewById(R.id.destination);
            viewHolder.dateView = (TextView) view.findViewById(R.id.pickup_time);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        TripListItem trip = this.tripList.get(position);


        viewHolder.nameView.setText(trip.getDriverName());
        viewHolder.pickupView.setText(trip.getPickup());
        viewHolder.destinationView.setText(trip.getDestination());
        return view;
    }
}
