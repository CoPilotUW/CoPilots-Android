package com.copilot.copilot.tripsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.copilot.copilot.R;
import com.copilot.copilot.RiderJson;
import com.copilot.copilot.listitems.TripListItem;
import com.copilot.copilot.listitems.TripListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaozhuoyu on 2017-05-27.
 */

public class PoolActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    // Declare Variables
    EditText pickupPicker;
    EditText destinationPicker;

    ListView listView;
    TripListViewAdapter adapter;
    SearchView editsearch;

    LinearLayout searchWidget;

    PoolSearchDatePicker datePicker;
    PoolSearchStartingTimePicker pickupTimePicker;

    List<TripListItem> tripList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool);

        // pass results obtained from the previous screen to this one
        String pickup = getIntent().hasExtra("from") ? getIntent().getStringExtra("from") : "";
        String destination = getIntent().hasExtra("to") ? getIntent().getStringExtra("to") : "";
        int year = getIntent().getIntExtra("year", -1);
        int month = getIntent().getIntExtra("month", -1);
        int day = getIntent().getIntExtra("day", -1);
        int tripHour = getIntent().getIntExtra("hour", -1);
        int tripMinute = getIntent().getIntExtra("minute", -1);

        Calendar lastDate = null;
        if (year != -1) {
            lastDate = Calendar.getInstance();
            lastDate.set(year, month, day);
        }

        // so is the intent here to instantiate the sub-search view with those fields as an intent??

        // locate our views and initialize them
        listView = (ListView) findViewById(R.id.trip_list);


        String rawTripsResponse = getIntent().getStringExtra("tripsListResponse");
        tripList = parseFilteredTrips(rawTripsResponse);



        searchWidget = (LinearLayout) findViewById(R.id.trip_search_widget);
        searchWidget.clearFocus();
        searchWidget.setVisibility(View.GONE);

        // TODO: pretty sure I should not be setting stuff here as per best practices >_>
        TextView pickupSearchView = (TextView) searchWidget.findViewById(R.id.trip_from);
        pickupSearchView.setText(pickup);

        TextView destinationSearchView = (TextView) searchWidget.findViewById(R.id.trip_to);
        destinationSearchView.setText(destination);

        TextView tripTimeSearchView = (TextView) searchWidget.findViewById(R.id.trip_time);
        tripTimeSearchView.setText(tripHour + ":" + tripMinute);

        TextView tripDateSearchView = (TextView) searchWidget.findViewById(R.id.trip_date);

        // apparently this cannot format strings... ugh (for now)
        //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tripDateSearchView.setText("");

        // get pool_search_wrapper as layout to add to
        editsearch = (SearchView) findViewById(R.id.pool_search_bar);
        editsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout sw = (LinearLayout) findViewById(R.id.trip_search_widget);

                int visibility = sw.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
                sw.setVisibility(visibility);
            }
        });

        adapter = new TripListViewAdapter(this, tripList, pickup, destination, lastDate, tripHour, tripMinute);
        listView.setAdapter(adapter);



    }

    private List<TripListItem> parseFilteredTrips(String rawTripResponse) {
        JSONArray jsonResponse = new JSONArray();
        List<TripListItem> parsedTrips = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try {
            jsonResponse = new JSONArray(rawTripResponse);
            for (int i = 0; i < jsonResponse.length(); i++) {
                JSONObject rawTrip = jsonResponse.getJSONObject(i);

                String tripID = rawTrip.getString("id");
                String pickup = rawTrip.getString("source");
                String destination = rawTrip.getString("destination");

                // 2017-07-18T19:23:41.000Z
                String rawTripDate = rawTrip.getString("from_date");
                Date parsedDate = dateFormat.parse(rawTripDate);


                // blindly assume this works (for now)
                JSONObject driver = rawTrip.getJSONArray("CPUsers").getJSONObject(0);
                String driverID = driver.getString("id");
                String driverName = driver.getString("first_name") + " " + driver.getString("last_name");

                // rating pertains to the driver rating field, unused for now...

                // name, pickup, destination, date, time
                // need: tripID, driverID,
                parsedTrips.add(new TripListItem(
                   tripID,
                   driverID,
                   driverName,
                   pickup,
                   destination,
                   parsedDate
                ));
            }
        } catch (JSONException e) {
            Log.d("PoolActivity", e.getMessage());
            return new ArrayList<>();
        } catch (ParseException e) {
            Log.d("PoolActivity", e.getMessage());
            return new ArrayList<>();
        }
        return parsedTrips;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
