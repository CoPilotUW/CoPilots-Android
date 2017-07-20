package com.copilot.copilot.tripsearch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.copilot.copilot.R;
import com.copilot.copilot.RiderPool;
import com.copilot.copilot.TripDetails;
import com.copilot.copilot.invitationlist.InvitationList;
import com.copilot.helper.CPUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adityasridhar on 2017-07-19.
 */

public class RoleViewListAdapter extends BaseAdapter {
    Context context;
    JSONArray trips;

    LayoutInflater inflater;
    private Intent nextIntent = null;

    public RoleViewListAdapter(Context context, JSONArray trips) {
        this.context = context;
        this.trips = trips;
    }

    public int getCount() {
        return trips.length();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView source;
        TextView destination;
        TextView travel_time;
        String groupId = "";
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.activity_role_view_list_item, parent, false);

        // Locate the TextViews in listview_item.xml
        source = (TextView) itemView.findViewById(R.id.source);
        destination = (TextView) itemView.findViewById(R.id.destination);
        travel_time = (TextView) itemView.findViewById(R.id.travel_time);

        JSONObject obj;
        try {
            obj = trips.getJSONObject(position);
            groupId = obj.getString("id");
            source.setText(obj.getString("source"));
            destination.setText(obj.getString("destination"));
            travel_time.setText(obj.getString("from_date").substring(0,10));
        } catch (JSONException e) {

        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = trips.getJSONObject(position);
                    String groupId_2 = obj.getString("id");
                    nextIntent = new Intent(context, TripDetails.class);
                    nextIntent.putExtra("cpgroupid", groupId_2);
                    context.startActivity(nextIntent);
                } catch (Exception e) {
                }
            }
        });

        return itemView;
    }
}
