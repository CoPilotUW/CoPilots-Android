package com.copilot.copilot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by xiaozhuoyu on 2017-07-14.
 */

public class RiderPoolListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    String[] users;
    LayoutInflater inflater;

    public RiderPoolListAdapter(Context context, String[] users) {
        this.context = context;
        this.users = users;
    }

    public int getCount() {
        return users.length;
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

        View itemView = inflater.inflate(R.layout.activity_rider_pool_list_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtrank = (TextView) itemView.findViewById(R.id.rank);

        // Capture position and set to the TextViews
        txtrank.setText(users[position]);

        ImageButton messageButton = (ImageButton) itemView.findViewById(R.id.message_button);

        messageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });


        return itemView;
    }
}
