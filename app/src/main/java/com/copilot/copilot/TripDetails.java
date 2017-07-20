package com.copilot.copilot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.Toast;
import android.widget.RatingBar;
import android.widget.TextView;
import android.app.Dialog;
import android.widget.LinearLayout;

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
    private int count;
    ArrayList<TextView> passengers = new ArrayList<TextView>();

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

                    TextView tx = new TextView(getApplicationContext());
                    if (userGroup.getBoolean("isDriver")) {
                        tx.setText(user.getString("first_name") + " " + user.getString("last_name"));
                    } else {
                        tx.setText(user.getString("first_name") + " " + user.getString("last_name"));
                    }

                    passengers.add(tx);
                    System.out.println(user.getString("first_name"));
                }

                TextView tx_1 = new TextView(getApplicationContext());
                tx_1.setText("John Smith");

                TextView tx_2 = new TextView(getApplicationContext());
                tx_2.setText("Barack Obama");
                passengers.add(tx_1);
                passengers.add(tx_2);
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

        setup(successCallback);
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

        final Button endTripButton = (Button) findViewById(R.id.end_trip_button);
        endTripButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog rankDialog = new Dialog(TripDetails.this);
                rankDialog.setCancelable(true);
                rankDialog.setContentView(R.layout.rank_dialog);
                rankDialog.setCancelable(false);
                rankDialog.setCanceledOnTouchOutside(false);
                final Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);

                LinearLayout rankLayout = rankDialog.findViewById(R.id.rank_dialog_button_lin_layout);
                count = passengers.size();

                for (TextView tx : passengers) {
                    RatingBar ratingBar = new RatingBar(TripDetails.this, null, android.R.attr.ratingBarStyle);
                    ratingBar.setScaleX(0.5f);
                    ratingBar.setScaleY(0.5f);
                    ratingBar.setPadding(0, 0, 0, 0);

                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating,
                                                    boolean fromUser) {
                            count--;
                            if (count <= 0) {
                                updateButton.setBackgroundColor(Color.parseColor("#444f4f"));
                                updateButton.setEnabled(true);
                            }
                        }
                    });


                    LinearLayout layout = new LinearLayout(TripDetails.this);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setGravity(Gravity.RIGHT);


                    tx.setTextColor(Color.WHITE);
                    tx.setGravity(Gravity.LEFT);
                    tx.setPadding(60, 44, 0, 0);

                    layout.addView(tx);
                    layout.addView(ratingBar);

                    rankLayout.addView(layout);
                }

                updateButton.setBackgroundColor(Color.parseColor("#444f4f"));
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        endTripButton.setEnabled(false);
                        endTripButton.setText("Rating Submitted!");
                        endTripButton.setBackgroundColor(Color.parseColor("#222F2F"));
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                updateButton.setEnabled(false);
                rankDialog.show();
                Window window = rankDialog.getWindow();
                window.setLayout(1100, 1100);

                updateButton.setEnabled(true);
            }
        });
    }

    private void setup(VolleyCallback callback) {
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

        headers.put("x-access-token", accessToken);

        params.put("cpgroupid", groupID);

        request.makeGetRequest(GlobalConstants.GET_TRIP_DETAILS, params, callback, failure, headers);
    }
}
