package com.copilot.copilot.tripsearch;

import android.content.Context;
import android.widget.TimePicker;

import com.copilot.copilot.listitems.RiderListViewAdapterOld;

/**
 * Created by xiaozhuoyu on 2017-05-21.
 */
public class PoolSearchEndingTimePicker extends PoolSearchTimePicker {
    public PoolSearchEndingTimePicker(Context context, int editTextViewID, RiderListViewAdapterOld adapter) {
        super(context, editTextViewID, adapter);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        _hour = hourOfDay;
        _minute = minute;
        updateDisplay();

        _adapter.filterByTime(-1, -1, _minute, _hour);
    }
}
