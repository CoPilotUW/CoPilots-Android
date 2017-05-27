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

public class BookingActivity extends AppCompatActivity {
    private Calendar calendar;
    private EditText dateFieldText;
    private EditText fromTimeField;
    private EditText toTimeField;
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private int fromHour = -1;
    private int toHour = -1;
    private int fromMinute = -1;
    private int toMinute = -1;

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

        dateFieldText = (EditText) findViewById(R.id.dateField);
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
                fireFromTimePicker(v, fromTimeField);
            }
        });

        toTimeField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fireToTimePicker(v, toTimeField);
            }
        });
    }

    private void fireFromTimePicker(View v, final EditText timeField) {
        Calendar currentTime = Calendar.getInstance();
        fromHour = currentTime.get(Calendar.HOUR_OF_DAY);
        fromMinute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeField.setText(selectedHour + ":" + selectedMinute);
            }
        }, fromHour, fromMinute, true);
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    private void fireToTimePicker(View v, final EditText timeField) {
        Calendar currentTime = Calendar.getInstance();
        toHour = currentTime.get(Calendar.HOUR_OF_DAY);
        toMinute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePicker;
        timePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                timeField.setText(selectedHour + ":" + selectedMinute);
            }
        }, toHour, toMinute, true);
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
        dateFieldText.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }

    public void clickTripBookingFormButton(View view)
    {
        // Intent to fire the pool activity.
        Intent fireGroups = new Intent(this, PoolActivity.class);
        switch(view.getId())
        {
            case R.id.cancelButton:
                break;
            case R.id.submitButton:
                // When firing the pool activity put the user selections into extra.
                // fireGroups.putExtra("...", "...");
                if (year != -1) {
                    fireGroups.putExtra("year", year);
                }

                if (month != -1) {
                    fireGroups.putExtra("month", month);
                }

                if (day != -1) {
                    fireGroups.putExtra("day", day);
                }

                if (fromTimeField.getText().length() > 0) {
                    fireGroups.putExtra("from", fromTimeField.getText());
                }

                if (toTimeField.getText().length() > 0) {
                    fireGroups.putExtra("to", toTimeField.getText());
                }

                if (fromHour != -1) {
                    fireGroups.putExtra("fromHour", fromHour);
                }

                if (fromMinute != -1) {
                    fireGroups.putExtra("fromMinute", fromMinute);
                }

                if (toHour != -1) {
                    fireGroups.putExtra("toHour", toHour);
                }

                if (toMinute != -1) {
                    fireGroups.putExtra("toMinute", toMinute);
                }

                startActivity(fireGroups);

                // TODO Make request to server
                break;
            default:
                Log.e("BookingActivity", "Invalid button clicked");
        }
    }
}
