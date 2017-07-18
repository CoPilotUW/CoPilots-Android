package com.copilot.copilot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.helper.CPUtility;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */


public class TripDetails extends AppCompatActivity {
    private HTTPRequestWrapper request;
    private ViewPager viewPager;
    private TripDetailsPagerAdapter adapter;
    private String groupID;
    private TabLayout tabLayout;
    private TextView driverName;

    final VolleyCallback successCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            // If we are creating a group then put the trip information into the riderpool screen.
            // Parse the json response that we get back.
            JSONObject parsedResponse = null;
            try {
                parsedResponse = new JSONObject(response);
                JSONArray users = parsedResponse.getJSONArray("CPUsers");
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    JSONObject userGroup = user.getJSONObject("cpusergroups");
                    if (userGroup.getBoolean("isDriver")) {
                        driverName.setText(user.getString("first_name") + " " + user.getString("last_name"));
                    }
                }
            } catch (JSONException e) {

            }
        }
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Toast.makeText(TripDetails.this, "Could not fetch the trip :( try again later! " + response, Toast.LENGTH_SHORT).show();
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.trip_details_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Trip"));
        tabLayout.addTab(tabLayout.newTab().setText("Members"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        driverName = (TextView) findViewById(R.id.driver_name);

        viewPager = (ViewPager) findViewById(R.id.pager);

        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, this);

        groupID = getIntent().getStringExtra("cpgroupid");

        setup();
        adapter = new TripDetailsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), getIntent().getStringExtra("cpgroupid").toString());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setup() {
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

        headers.put("x-access-token", accessToken);

        params.put("cpgroupid", groupID);

        request.makeGetRequest(GlobalConstants.GET_TRIP_DETAILS, params, successCallback, failure, headers);
    }
}
