package com.copilot.copilot.tripsearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.copilot.copilot.R;
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

    public View getView(int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView name;
        TextView destination;
        TextView time;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // TODO This should be a list item specific to trip view
        View itemView = inflater.inflate(R.layout.activity_rider_pool_list_item, parent, false);

        // Locate the TextViews in listview_item.xml
        name = (TextView) itemView.findViewById(R.id.name);
        destination = (TextView) itemView.findViewById(R.id.destination);
        time = (TextView) itemView.findViewById(R.id.time);

        JSONObject obj;
        JSONObject userObject;
        try {
            obj = trips.getJSONObject(position);
            userObject = obj.getJSONObject("CPUser");
            name.setText(userObject.getString("first_name") + " " + userObject.getString("last_name"));
            destination.setText(obj.getString("destination"));
            time.setText(CPUtility.getDateTimeString(obj.getString("date"), obj.getString("time")));
        } catch (JSONException e) {

        }

        ImageButton messageButton = (ImageButton) itemView.findViewById(R.id.message_button);

        messageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call the apis that sends the invite.
            }
        });


        return itemView;
    }
}
