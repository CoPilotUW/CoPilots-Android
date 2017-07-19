package com.copilot.copilot.listitems;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.copilot.copilot.R;
import com.copilot.copilot.modals.InvitationModal;
import com.copilot.listeners.InvitationOnClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by kjiang on 2017-06-20.
 */

public class TripListViewAdapter extends BaseAdapter {
    Activity parent;
    LayoutInflater inflater;

    private List<TripListItem> tripList = new ArrayList<>();

    public TripListViewAdapter(
            Activity parent,
        List<TripListItem> tripList
    ) {
        this.parent = parent;
        this.inflater = LayoutInflater.from(this.parent);

        this.tripList.addAll(tripList);
    }

    public class ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView pickupView;
        TextView destinationView;
        TextView dateView;
        ImageButton requestButton;
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

        if (view == null || view.getTag() == null) {
            viewHolder = new ViewHolder();

            view = inflater.inflate(R.layout.trip_screen_trip, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.riderImage);

            viewHolder.nameView = (TextView) view.findViewById(R.id.driverName);
            viewHolder.pickupView = (TextView) view.findViewById(R.id.pickup);
            viewHolder.destinationView = (TextView) view.findViewById(R.id.destination);
            viewHolder.dateView = (TextView) view.findViewById(R.id.pickup_time);
            viewHolder.requestButton = (ImageButton) view.findViewById(R.id.request_button);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        TripListItem trip = this.tripList.get(position);


        viewHolder.nameView.setText(trip.getDriverName());
        viewHolder.pickupView.setText(trip.getPickup());
        viewHolder.destinationView.setText(trip.getDestination());
        viewHolder.dateView.setText(trip.getPickupTimeStr());

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userID = sharedPref.getString("id", "");
        Log.d("TripListViewAdapter:", "user ID: " + userID);

        // TODO: include endpoint in here too
        viewHolder.requestButton.setOnClickListener(new InvitationOnClickListener(this.parent, userID, trip.getDriverID(), trip.getDriverName()));
        return view;
    }
}
