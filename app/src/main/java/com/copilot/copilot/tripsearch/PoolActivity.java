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

import org.json.JSONObject;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

    ArrayList<TripListItem> tripList = new ArrayList<>();
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


        // get the jsons (mock what we have in API)
        for (int i = 0; i < RiderJson.tripJSONs.length; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                JSONObject trip = new JSONObject(RiderJson.tripJSONs[i]);
                tripList.add(new TripListItem(
                    trip.getString("name"),
                    trip.getString("pickup"),
                    trip.getString("destination"),
                    sdf.parse(trip.getString("tripDate")),
                    trip.getString("pickupTime")
                ));
            } catch (Exception e){
                Log.v("test", e.toString());
            }
        }

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


//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pool);
//
//        // Locate the ListView in listview_main.xml
//        list = (ListView) findViewById(R.id.listview);
//
//        datePicker = (EditText) findViewById(R.id.datePicker);
//
//
//        // Pass results to ListViewAdapter Class
//        int lastStartingHour = getIntent().getIntExtra("fromHour", -1);
//        int lastStartingMinute = getIntent().getIntExtra("fromMinute", -1);
//        int lastEndingHour = getIntent().getIntExtra("toHour", -1);
//        int lastEndingMinute = getIntent().getIntExtra("toMinute", -1);
//        String lastLocation = !getIntent().hasExtra("destination") ? null : getIntent().getStringExtra("destination");
//
//        Calendar lastDate = null;
//
//        // Locate the EditText in listview_main.xml
//        editsearch = (SearchView) findViewById(R.id.search);
//        editsearch.setOnQueryTextListener(this);
//
//        editsearch.setQueryHint("Search for rides...");
//        int searchPlateId = editsearch.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = editsearch.findViewById(searchPlateId);
//        if (searchPlate!=null) {
//            searchPlate.setBackgroundColor(Color.DKGRAY);
//            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
//            if (searchText!=null) {
//                searchText.setTextColor(Color.WHITE);
//                searchText.setHintTextColor(Color.WHITE);
//            }
//        }
//
//        // Create the appropriate search filter views
//        picker = new PoolSearchDatePicker(this, R.id.datePicker, adapter);
//
//        startingPicker = new PoolSearchStartingTimePicker(this, R.id.beginningTimePicker, adapter);
//
//        endingTimePicker = new PoolSearchEndingTimePicker(this, R.id.endingTimePicker, adapter);
//
//        // Display the correct information from the booking screen.
//        if (lastStartingHour != -1) {
//            startingPicker.setText(lastStartingMinute, lastStartingHour);
//        }
//
//        if (lastEndingHour != -1) {
//            endingTimePicker.setText(lastEndingMinute, lastEndingHour);
//        }
//
//        if (lastDate != null) {
//            picker.setText(year, month, day);
//        }
//    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
