package com.copilot.copilot.listitems;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.copilot.copilot.R;
import com.copilot.copilot.modals.InvitationModal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by kjiang on 2017-06-20.
 */

public class TripListViewAdapter extends BaseAdapter {
    Activity parent;
    LayoutInflater inflater;

    private List<TripListItem> tripList = new ArrayList<>();

    private String fromLocation = "";
    private String toLocation = "";

    private Calendar rideDate = null;
    private int rideHour = -1;
    private int rideMinute = -1;

    // TODO: add other filtering fields here as needed
    public TripListViewAdapter(
            Activity parent,
        List<TripListItem> tripList,
        String fromLocation,
        String toLocation,
        Calendar rideDate,
        int rideHour,
        int rideMinute
    ) {
        this.parent = parent;
        this.inflater = LayoutInflater.from(this.parent);

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

    public class InvitationOnClickListener implements View.OnClickListener {
        String inviter;
        String recipient;
        Activity parent;

        public InvitationOnClickListener(Activity parent, String inviter, String recipient) {
            this.inviter = inviter;
            this.recipient = recipient;
            this.parent = parent;
        }

        @Override
        public void onClick(View v) {
            InvitationModal modal = InvitationModal.newInstance(inviter, recipient);
            modal.show(parent.getFragmentManager(), "invite_modal");
        }
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

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        TripListItem trip = this.tripList.get(position);


        viewHolder.nameView.setText(trip.getDriverName());
        viewHolder.pickupView.setText(trip.getPickup());
        viewHolder.destinationView.setText(trip.getDestination());
        viewHolder.dateView.setText(trip.getPickupTimeStr());


//        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        viewHolder.imageView.setOnClickListener(new InvitationOnClickListener(this.parent, "Kelvin", trip.getDriverName()));
        return view;
    }
}
