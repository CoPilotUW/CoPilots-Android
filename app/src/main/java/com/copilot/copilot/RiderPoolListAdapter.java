package com.copilot.copilot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.helper.CPUtility;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.listeners.InvitationOnClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaozhuoyu on 2017-07-14.
 */

public class RiderPoolListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    JSONArray tripSearches;
    LayoutInflater inflater;
    String groupId;
    HTTPRequestWrapper request;
    String token;
    Activity parentActivity;

    public RiderPoolListAdapter(Activity parentActivity, Context context, JSONArray tripSearches, String groupID, HTTPRequestWrapper request, String token) {
        this.context = context;
        this.tripSearches = tripSearches;
        this.groupId = groupID;
        this.request = request;
        this.token = token;
        this.parentActivity = parentActivity;
    }

    public int getCount() {
        return tripSearches.length();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Declare Variables
        TextView name;
        TextView destination;
        TextView time;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.activity_rider_pool_list_item, parent, false);

        // Locate the TextViews in listview_item.xml
        name = (TextView) itemView.findViewById(R.id.name);
        destination = (TextView) itemView.findViewById(R.id.destination);
        time = (TextView) itemView.findViewById(R.id.time);

        JSONObject obj;
        JSONObject userObject;
        try {
            obj = tripSearches.getJSONObject(position);
            userObject = obj.getJSONObject("CPUser");
            name.setText(userObject.getString("first_name") + " " + userObject.getString("last_name"));
            destination.setText(obj.getString("destination"));
            time.setText(CPUtility.getDateTimeString(obj.getString("date"), obj.getString("time")));
        } catch (JSONException e) {

        }

        ImageButton messageButton = (ImageButton) itemView.findViewById(R.id.message_button);

        try {
            obj = tripSearches.getJSONObject(position);
            userObject = obj.getJSONObject("CPUser");
            String userID = userObject.getString("id");

            // third argument is the recipient ID, sorry!
            messageButton.setOnClickListener(new InvitationOnClickListener(parentActivity, groupId, userID, userObject.getString("first_name"), GlobalConstants.REQUEST_RIDE));
        } catch (JSONException e) {
            Log.d("adfadf", "did we run into an exception");
        }
//        messageButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Call the apis that sends the invite.
//                Log.d("afda", "is this being called??");
//                try {
//                    JSONObject obj = tripSearches.getJSONObject(position);
//                    JSONObject userObject = obj.getJSONObject("CPUser");
//                    String userID = userObject.getString("id");
//                    new InvitationOnClickListener(parentActivity, groupId, "", userID, GlobalConstants.REQUEST_RIDE);
//                } catch (JSONException e) {
//                    Log.d("adfadf", "did we run into an exception");
//                }
//
//            }
//        });


        return itemView;
    }
}
