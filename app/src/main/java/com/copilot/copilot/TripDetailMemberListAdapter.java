package com.copilot.copilot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailMemberListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    JSONArray users;
    LayoutInflater inflater;
    String groupId;

    public TripDetailMemberListAdapter(Context context, JSONArray users, String groupId) {
        this.context = context;
        this.users = users;
        this.groupId = groupId;
    }

    public int getCount() {
        return users.length();
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

        // Capture position and set to the TextViews
        try {
            txtrank.setText(users.getJSONObject(position).getString("first_name") + " " + users.getJSONObject(position).getString("last_name"));
        } catch (JSONException e) {

        }


        ImageButton messageButton = (ImageButton) itemView.findViewById(R.id.message_button);
        ImageButton toggleButton = (ImageButton) itemView.findViewById(R.id.toggle_button);
        toggleButton.setTag("minus");

        toggleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Detect which image it currently is.
                ImageButton toggleButton = (ImageButton) v.findViewById(R.id.toggle_button);

                if (toggleButton != null && toggleButton.getTag() == "minus") {
                    toggleButton.setImageResource(R.drawable.remove_2);
                }
            }
        });


        return itemView;
    }
}
