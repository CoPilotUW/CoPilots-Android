package com.copilot.copilot;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.copilot.copilot.tripsearch.PoolActivity;

public class BookingActivity extends AppCompatActivity {
    private Calendar calendar;
    private EditText fromField;
    private EditText toField;
    private EditText dateFieldText;
    private EditText timeField;
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int hour = -1;
    private int minute = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("Role") && getIntent().getStringExtra("Role").equals("Rider")) {
            setContentView(R.layout.activity_rider_booking);
        } else if (getIntent().hasExtra("Role") && getIntent().getStringExtra("Role").equals("Driver")) {
            setContentView(R.layout.activity_driver_booking);
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
        dateFieldText.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    public void clickTripBookingFormButton(View view)
    {
        // Intent to fire the pool activity.
        Intent fireGroups = new Intent(this, PoolActivity.class);
        switch(view.getId())
        {
            case R.id.submitButton:
                if (year != -1) {
                    fireGroups.putExtra("year", year);
                }

                if (month != -1) {
                    fireGroups.putExtra("month", month);
                }

                if (day != -1) {
                    fireGroups.putExtra("day", day);
                }

                if (fromField.getText().length() > 0) {
                    fireGroups.putExtra("from", fromField.getText());
                }

                if (toField.getText().length() > 0) {
                    fireGroups.putExtra("to", toField.getText());
                }

                if (hour != -1) {
                    fireGroups.putExtra("hour", hour);
                }

                if (minute != -1) {
                    fireGroups.putExtra("minute", minute);
                }

                startActivity(fireGroups);

                // TODO Make request to server
                break;
            default:
                Log.e("BookingActivity", "Invalid button clicked");
        }
    }
}
