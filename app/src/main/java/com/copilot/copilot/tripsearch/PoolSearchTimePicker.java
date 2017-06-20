package com.copilot.copilot.tripsearch;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.*;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.copilot.copilot.listitems.RiderListViewAdapterOld;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by xiaozhuoyu on 2017-05-19.
 */
public abstract class PoolSearchTimePicker implements View.OnClickListener, OnTimeSetListener{
    protected EditText _editText;
    protected int _hour;
    protected int _minute;
    protected Context _context;
    protected RiderListViewAdapterOld _adapter;


    public PoolSearchTimePicker(Context context, int editTextViewID, RiderListViewAdapterOld adapter)
    {
        Activity act = (Activity)context;
        this._editText = (EditText)act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
        this._adapter = adapter;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        TimePickerDialog tp1 = new TimePickerDialog(_context, this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        tp1.show();
    }

    protected void updateDisplay() {
        _editText.setText(new StringBuilder()
                .append(_hour).append(":").append(_minute));
    }

    public void setText(int minute, int hour) {
        _hour = hour;
        _minute = hour;
        updateDisplay();
    }
}
