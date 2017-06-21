package com.copilot.copilot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailMemberListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] rank;
    String[] country;
    String[] population;
    LayoutInflater inflater;

    public TripDetailMemberListAdapter(Context context, String[] rank, String[] country,
                           String[] population) {
        this.context = context;
        this.rank = rank;
        this.country = country;
        this.population = population;
    }

    public int getCount() {
        return rank.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtrank;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.trip_details_member_fragment_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtrank = (TextView) itemView.findViewById(R.id.rank);
        // Locate the ImageView in listview_item.xml

        // Capture position and set to the TextViews
        txtrank.setText(rank[position]);

        return itemView;
    }
}
