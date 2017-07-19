package com.copilot.copilot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailsMembersFragment extends Fragment {
    // Hard coded json for trip member.
    ListView list;
    TripDetailMemberListAdapter adapter;
    Button showRiderPool;
    String groupId;
    HTTPRequestWrapper request;
    JSONObject tripDetails;
    JSONArray users;
    View rootView;

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

            setup();

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
        users = new JSONArray();
        getTripDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.trip_details_member_fragment, container,
                false);

        return rootView;
    }


    public void showRiderPool(View view)
    {
        Intent riderPoolIntent = new Intent(this.getActivity(), RiderPool.class);
        riderPoolIntent.putExtra("cpgroupid", groupId);
        startActivity(riderPoolIntent);
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

    private void setup() {
        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.trip_details_member_fragment_list);

        // First get the user id.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userId = sharedPref.getString(GlobalConstants.USER_ID, "");

        try {
            users = tripDetails.getJSONArray("CPUsers");
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                JSONObject userGroup = user.getJSONObject("cpusergroups");
                Log.d("afasdf", "is the length of the users greater than 0?");
                Log.d("asfasdf", "the user_id is: " + user.getString("id"));
                Log.d("asdfasf", "the stored id is: " + userId);
                Log.d("adsfsaf", "the userGroup is driver is: " + userGroup.getString("isDriver"));
                if (userGroup.getString("isDriver").equals("true") && user.getString("id").equals(userId)) {
                    Log.d("afasdf", "did we go into this statement?");
                    if (showRiderPool == null) {
                        showRiderPool = new Button(this.getContext());
                        showRiderPool.setText("Show Riders!");
                        showRiderPool.setBackgroundColor(getResources().getColor(R.color.editTextColor));

                        list.addFooterView(showRiderPool);

                        showRiderPool.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showRiderPool(v);
                            }
                        });
                    }
                }
            }
        } catch (JSONException e) {

        }

        // Pass results to ListViewAdapter Class
        adapter = new TripDetailMemberListAdapter(getContext(), users, groupId);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture clicks on ListView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }

        });
    }
}
