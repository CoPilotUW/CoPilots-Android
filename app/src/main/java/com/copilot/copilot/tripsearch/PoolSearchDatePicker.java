package com.copilot.copilot.tripsearch;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.app.DatePickerDialog.*;
import android.widget.EditText;

import com.copilot.copilot.listitems.RiderListViewAdapterOld;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * Created by xiaozhuoyu on 2017-05-19.
 */
public class PoolSearchDatePicker implements View.OnClickListener, OnDateSetListener {
    EditText _editText;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;
    private RiderListViewAdapterOld _adapter;

    public PoolSearchDatePicker(Context context, int editTextViewID, RiderListViewAdapterOld adapter)
    {
        Activity act = (Activity)context;
        this._editText = (EditText)act.findViewById(editTextViewID);
        this._editText.setOnClickListener(this);
        this._context = context;
        this._adapter = adapter;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        updateDisplay();
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        _adapter.filterByDate(cal);
    }
    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(_context, this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    // updates the date in the birth date EditText
    private void updateDisplay() {

        _editText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" "));
    }

    public void setText(int year, int month, int day) {
        _birthYear = year;
        _month = month;
        _day = day;
        updateDisplay();
    }
}