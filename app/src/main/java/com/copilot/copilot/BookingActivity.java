package com.copilot.copilot;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.copilot.tripsearch.PoolActivity;
import com.copilot.helper.CPUtility;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;
import com.facebook.CallbackManager;
import com.facebook.internal.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookingActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private HTTPRequestWrapper request;

    private Calendar calendar;
    private EditText fromField;
    private EditText toField;
    private EditText dateFieldText;
    private EditText timeField;
    private EditText carModelField;
    private EditText carNumber;
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;
    private boolean isDriver = false;
    private Intent nextIntent = null;

    // do nothing when API call completes
    final VolleyCallback dummyCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
//        finish();
        }
    };

    final VolleyCallback successCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            // If we are creating a group then put the trip information into the riderpool screen.
            // Parse the json response that we get back.
            if (isDriver) {
                String CPGroupId = "";
                JSONObject parsedResponse = null;
                try {
                    parsedResponse = new JSONObject(response);
                    nextIntent.putExtra("cpgroupid", parsedResponse.getString("id"));
                } catch (JSONException e) {

                }

            }
            startActivity(nextIntent);
            finish();
        }
    };

    final VolleyCallback tripSearchSuccessCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            // assume JSON array
            Log.d("TRIP SEARCH", "trip search response: " + response);
            nextIntent.putExtra("tripsListResponse", response);
            // so maybe it's actually better to pass the trips list in here don't you think?
            startActivity(nextIntent);

            // TODO: do integration here for trip search
            finish();
        }
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Toast.makeText(getApplicationContext(), "Could not create the trip :( try again later! " + response, Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("Role") && getIntent().getStringExtra("Role").equals("Rider")) {
            setContentView(R.layout.activity_rider_booking);
        } else if (getIntent().hasExtra("Role") && getIntent().getStringExtra("Role").equals("Driver")) {
            setContentView(R.layout.activity_driver_booking);
            isDriver = true;
        } else {
            setContentView(R.layout.activity_rider_booking);
            Log.e("BookingActivity", "Expected intent to contain a valid role extra, but was found not to be.");
        }

        fromField = (EditText) findViewById(R.id.fromField);
        toField = (EditText) findViewById(R.id.toField);
        dateFieldText = (EditText) findViewById(R.id.dateField);
        timeField = (EditText) findViewById(R.id.timeField);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        timeField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fireTimePicker(v, timeField);
            }
        });

        callbackManager = CallbackManager.Factory.create();

        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, BookingActivity.this);

        nextIntent = new Intent(this, PoolActivity.class);

        if (isDriver) {
            carModelField = (EditText) findViewById(R.id.carModelField);
            carNumber = (EditText) findViewById(R.id.carNumber);
            nextIntent = new Intent(this, TripDetails.class);
        }

    }

    private void fireTimePicker(View v, final EditText timeField) {
        Calendar currentTime = Calendar.getInstance();
        hour = currentTime.get(Calendar.HOUR_OF_DAY);
        minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(BookingActivity.this, R.style.DialogPickerTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeField.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this, R.style.DialogPickerTheme, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        // TODO: @Aditya Sridhar: PLEASE FIX AND ADD A DATEPICKER IN HERE!
        dateFieldText.setText(new StringBuilder().append(month).append("/").append(day).append("/").append(year));
    }

    public void clickTripBookingFormButton(View view)
    {
        switch(view.getId())
        {
            case R.id.submitButton:
                if (year != -1) {
                    nextIntent.putExtra("year", year);
                }

                if (month != -1) {
                    nextIntent.putExtra("month", month);
                }

                if (day != -1) {
                    nextIntent.putExtra("day", day);
                }

                if (fromField.getText().length() > 0) {
                    nextIntent.putExtra("from", fromField.getText().toString());
                }

                if (toField.getText().length() > 0) {
                    nextIntent.putExtra("to", toField.getText().toString());
                }

                if (hour != -1) {
                    nextIntent.putExtra("hour", hour);
                }

                if (minute != -1) {
                    nextIntent.putExtra("minute", minute);
                }

                // Make a server call to post the data;
                Map<String, String> params = new HashMap<String, String>();
                Map<String, String> headers = new HashMap<String, String>();

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

                headers.put("x-access-token", accessToken);

                String source = fromField.getText().toString();
                if (!source.isEmpty() && !source.equals("")) {
                    params.put("source", source);
                }

                String destination = toField.getText().toString();
                if (!destination.isEmpty() && !destination.equals("")) {
                    params.put("destination", destination);
                }

                if (isDriver) {
                    // Drivers has additional fields to fill in.
                    params.put("car_make", carNumber.getText().toString());
                    params.put("car_model", carModelField.getText().toString());
                    params.put("from_date", CPUtility.getDateStringForPost(year, month, day, hour, minute));
                    params.put("to_date", CPUtility.getDateStringForPost(year, month, day, hour, minute));
                    params.put("drive_to_address", toField.getText().toString());
                    request.makePostRequest(GlobalConstants.CREATE_TRIP_GROUP, params, successCallback, failure, headers);
                } else {
                    // TODO: @Aditya Sridhar, I blame you for this! - Kelvin
                    String hackedTripDateForAPI = dateFieldText.getText().toString();

//                    String tripDateForAPI = CPUtility.getDateStringForTripSearch(year, month, day);

                    if (!hackedTripDateForAPI.isEmpty() && !hackedTripDateForAPI.equals("")) {
                        params.put("date", hackedTripDateForAPI);
                    }
//                    params.put("time", Integer.toString(hour) + ":" + Integer.toString(minute));
                    request.makePostRequest(GlobalConstants.CREATE_TRIP_SEARCH, params, dummyCallback, failure, headers);
                    // GET REQUEST TO RETURN TRIPS!

                    Log.d("BOOKINGACTIVITY", "Searching for trips with source " + source + ", destination " + destination + ", on date " + hackedTripDateForAPI);
                    request.makeGetRequest(GlobalConstants.SEARCH_ALL_TRIPS, params, tripSearchSuccessCallback, failure, headers);

                    // /rider/search/all
                }

                break;
            default:
                Log.e("BookingActivity", "Invalid button clicked");
        }
    }
}
