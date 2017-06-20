package com.copilot.copilot.tripsearch;

import android.content.Context;
import android.widget.TimePicker;

import com.copilot.copilot.listitems.RiderListViewAdapterOld;

/**
 * Created by xiaozhuoyu on 2017-05-21.
 */
public class PoolSearchStartingTimePicker extends PoolSearchTimePicker {
    public PoolSearchStartingTimePicker(Context context, int editTextViewID, RiderListViewAdapterOld adapter) {
        super(context, editTextViewID, adapter);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _hour = hourOfDay;
        _minute = minute;
        updateDisplay();

        // Since the user picked a starting time range we should update the filter list appropriately.
        _adapter.filterByTime(_minute, _hour, -1, -1);
    }
}
