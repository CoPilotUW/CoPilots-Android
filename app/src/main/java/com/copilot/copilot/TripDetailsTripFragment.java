package com.copilot.copilot;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailsTripFragment extends Fragment{
    TextView fromField;
    TextView toField;
    TextView date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_details_trip_frament, container, false);

        // Grab the arguments.
        Bundle args = getArguments();
        fromField = (TextView) rootView.findViewById(R.id.departure_field_for_trip_details);
        fromField.setText(args.getString("from", "FROM PLACE"));
        toField = (TextView) rootView.findViewById(R.id.travel_field_for_trip_details);
        toField.setText(args.getString("to", "TO PLACE"));
        date = (TextView) rootView.findViewById(R.id.departing_field_for_trip_details);
        date.setText(args.getString("date", "DATE FIELD"));

        return rootView;

    }
}
