package com.copilot.copilot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.copilot.helper.CPUtility;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetails extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.trip_details_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Trip"));
        tabLayout.addTab(tabLayout.newTab().setText("Members"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        String from = getIntent().getStringExtra("from").toString();
        String to = getIntent().getStringExtra("to").toString();
        int year = getIntent().getIntExtra("year", -1);
        int month = getIntent().getIntExtra("month", -1);
        int day = getIntent().getIntExtra("day", -1);
        int hour = getIntent().getIntExtra("hour", -1);
        int minute = getIntent().getIntExtra("minute", -1);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM d, ''yy");
        String formatted = CPUtility.formatDate(year, month, day);

        ArrayList<String> bookingDetails = new ArrayList<String>();
        bookingDetails.add(from);
        bookingDetails.add(to);
        String departureTime = Integer.toString(hour) + ":" + Integer.toString(minute);
        String date = departureTime + " " + formatted;
        bookingDetails.add(date);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TripDetailsPagerAdapter adapter = new TripDetailsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), bookingDetails);

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

}
