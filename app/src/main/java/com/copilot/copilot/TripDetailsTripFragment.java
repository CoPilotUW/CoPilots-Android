package com.copilot.copilot;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailsTripFragment extends Fragment{
    private String groupId;
    TextView fromField;
    TextView toField;
    TextView date;
    HTTPRequestWrapper request;
    JSONObject tripDetails;

    final VolleyCallback successCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            // If we are creating a group then put the trip information into the riderpool screen.
            // Parse the json response that we get back.
            Log.d("asdfasf", response);
            try {
                tripDetails = new JSONObject(response);
                setup();
            } catch (JSONException e) {
                tripDetails = new JSONObject();
            }

        }
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Toast.makeText(getContext(), "Could not fetch the trip :( try again later! " + response, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        groupId = args.getString("cpgroupid", "");
        Log.d("adfasf", "the cpgroupid is: " + groupId);
        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, getContext());
        tripDetails = new JSONObject();
        getTripDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_details_trip_frament, container, false);

        fromField = (TextView) rootView.findViewById(R.id.departure_field_for_trip_details);
        toField = (TextView) rootView.findViewById(R.id.travel_field_for_trip_details);
        date = (TextView) rootView.findViewById(R.id.departing_field_for_trip_details);

        return rootView;

    }

    private void setup() {
        try {
            fromField.setText(tripDetails.getString("source"));
            toField.setText(tripDetails.getString("destination"));
            date.setText(tripDetails.getString("from_date"));
        } catch (JSONException e) {

        }
    }

    private void getTripDetails() {
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

        headers.put("x-access-token", accessToken);

        params.put("cpgroupid", groupId);

        request.makeGetRequest(GlobalConstants.GET_TRIP_DETAILS, params, successCallback, failure, headers);
    }
}
