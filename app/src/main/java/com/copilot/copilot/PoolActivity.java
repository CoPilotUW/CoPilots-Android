package com.copilot.copilot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by xiaozhuoyu on 2017-05-27.
 */

public class PoolActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    // Declare Variables
    EditText datePicker;
    ListView list;
    RiderListViewAdapter adapter;
    SearchView editsearch;
    PoolSearchDatePicker picker;
    PoolSearchStartingTimePicker startingPicker;
    PoolSearchEndingTimePicker endingTimePicker;
    RatingBar ratingBar;

    ArrayList<RiderListItem> riderList = new ArrayList<RiderListItem>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);

        // Generate sample data

        // Find the editText calender view

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        datePicker = (EditText) findViewById(R.id.datePicker);

        for (int i = 0; i < RiderJson.riderJsons.length; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                JSONObject rider = new JSONObject(RiderJson.riderJsons[i]);
                Log.v("test", Float.toString(BigDecimal.valueOf(rider.getDouble("userRating")).floatValue()));
                riderList.add(new RiderListItem(rider.getString("username"), rider.getString("userDestination"), BigDecimal.valueOf(rider.getDouble("userRating")).floatValue(), sdf.parse(rider.getString("userDate")), rider.getString("startingTime"), rider.getString("endingTime")));
            } catch (Exception e){
                Log.v("test", e.toString());
            }
        }

        // Pass results to ListViewAdapter Class
        int lastStartingHour = getIntent().getIntExtra("fromHour", -1);
        int lastStartingMinute = getIntent().getIntExtra("fromMinute", -1);
        int lastEndingHour = getIntent().getIntExtra("toHour", -1);
        int lastEndingMinute = getIntent().getIntExtra("toMinute", -1);
        String lastLocation = !getIntent().hasExtra("destination") ? null : getIntent().getStringExtra("destination");
        int year = getIntent().getIntExtra("year", -1);
        int month = getIntent().getIntExtra("month", -1);
        int day = getIntent().getIntExtra("day", -1);



        Calendar lastDate = null;
        if (year != -1) {
            lastDate = Calendar.getInstance();
            lastDate.set(year, month, day);
        }

        adapter = new RiderListViewAdapter(this, riderList, lastStartingMinute, lastStartingHour, lastEndingMinute, lastEndingHour, lastDate, lastLocation);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        editsearch.setQueryHint("Search for rides...");
        int searchPlateId = editsearch.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = editsearch.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.WHITE);
            }
        }

        // Create the appropriate search filter views
        picker = new PoolSearchDatePicker(this, R.id.datePicker, adapter);

        startingPicker = new PoolSearchStartingTimePicker(this, R.id.beginningTimePicker, adapter);

        endingTimePicker = new PoolSearchEndingTimePicker(this, R.id.endingTimePicker, adapter);

        // Display the correct information from the booking screen.
        if (lastStartingHour != -1) {
            startingPicker.setText(lastStartingMinute, lastStartingHour);
        }

        if (lastEndingHour != -1) {
            endingTimePicker.setText(lastEndingMinute, lastEndingHour);
        }

        if (lastDate != null) {
            picker.setText(year, month, day);
        }

        // Set up the rating bar correctly.
        addListenerOnRatingBar();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filterByLocation(newText);
        return false;
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                adapter.filterByRating(rating);

            }
        });
    }
}
