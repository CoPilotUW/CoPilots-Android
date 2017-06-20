package com.copilot.copilot.listitems;

import android.content.Context;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kjiang on 2017-06-20.
 */

public class RiderListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;

    private List<RiderListItem> riderList = new ArrayList<>();

    private String fromLocation = "";
    private String toLocation = "";

    private Calendar rideDate = null;
    private int rideHour = -1;
    private int rideMinute = -1;

    // TODO: add other filtering fields here as needed

    public RiderListViewAdapter(Context context) {
        this.mContext = context;
    }

    public class ViewHolder {

    }

    @Override
    public int getCount() {
        return riderList.size();
    }

    @Override
    public RiderListItem getItem(int position) {
        return riderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO: actually get something like an ID
        return position;
    }
}
