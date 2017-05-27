package com.copilot.copilot;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class BookingActivity extends AppCompatActivity {
    private Calendar calendar;
    private EditText editText;
    private EditText fromTimeField;
    private EditText toTimeField;
    private int year, month, day;

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

        editText = (EditText) findViewById(R.id.dateField);
        fromTimeField = (EditText) findViewById(R.id.fromTimeField);
        toTimeField = (EditText) findViewById(R.id.toTimeField);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        fromTimeField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fireTimePicker(v, fromTimeField);
            }
        });

        toTimeField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fireTimePicker(v, toTimeField);
            }
        });
    }

    private void fireTimePicker(View v, final EditText timeField) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeField.setText(selectedHour + ":" + selectedMinute);
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
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
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
        editText.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    public void clickTripBookingFormButton(View view)
    {
        // Intent to fire Jameson's view
        // Intent fireGroups = new Intent(this, BookingActivity.class);
        switch(view.getId())
        {
            case R.id.cancelButton:
                break;
            case R.id.submitButton:
                // TODO fire next view with params in the intent
                // fireGroups.putExtra("...", "...");
                // startActivity(fireGroups);

                // TODO Make request to server
                break;
            default:
                Log.e("BookingActivity", "Invalid button clicked");
        }
    }
}
