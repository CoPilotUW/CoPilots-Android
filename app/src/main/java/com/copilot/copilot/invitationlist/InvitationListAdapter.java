package com.copilot.copilot.invitationlist;

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
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.copilot.R;
import com.copilot.helper.CPUtility;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaozhuoyu on 2017-07-16.
 */

public class InvitationListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    JSONArray invitations = null;
    LayoutInflater inflater;
    boolean isDriver;
    HTTPRequestWrapper request;
    String token;
    String currentId;

    final VolleyCallback successCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
        }
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
        }
    };


    public InvitationListAdapter(Context context, JSONArray invitations, boolean isDriver, HTTPRequestWrapper request, String token, String currentUser) {
        this.context = context;
        this.invitations = invitations;
        this.isDriver = isDriver;
        this.request = request;
        this.token = token;
        this.currentId = currentUser;
    }

    public int getCount() {
        int totalInvites = 0;
        try {
            for (int i = 0; i < invitations.length(); i++) {
                JSONObject group = invitations.getJSONObject(i);
                JSONArray users = group.getJSONArray("CPUsers");
                totalInvites += users.length();
            }
        } catch (JSONException e) {
            Log.d("afdf", "error getting the total count for invitation list adapter.");
        }

        return totalInvites;
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
        TextView from;
        TextView destination;
        TextView time;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.activity_invitation_list_item, parent, false);

        // Locate the TextViews in listview_item.xml
        name = (TextView) itemView.findViewById(R.id.name);
        from = (TextView) itemView.findViewById(R.id.from);
        destination = (TextView) itemView.findViewById(R.id.destination);
        time = (TextView) itemView.findViewById(R.id.pickup_time);

        try {
            // Loop through the response until the index matches.
            int cur = 0;
            for (int i = 0; i < invitations.length(); i++) {
                JSONObject group = invitations.getJSONObject(i);
                JSONArray users = group.getJSONArray("CPUsers");
                for (int j = 0; j < users.length(); j++) {
                    if (cur == position) {
                        // Update each individual list item.
                        final JSONObject user = users.getJSONObject(j);
                        name.setText(user.getString("first_name") + " " + user.getString("last_name"));
                        from.setText(group.getString("source"));
                        destination.setText(group.getString("destination"));
                        time.setText(CPUtility.convertServerDateString(group.getString("from_date")));

                        ImageButton acceptButton = (ImageButton) itemView.findViewById(R.id.invitation_accept_button);
                        ImageButton declineButton = (ImageButton) itemView.findViewById(R.id.invitation_decline_button);

                        acceptButton.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // Make the network request to accept this invitation.
                                String userId = "";
                                String groupId = "";
                                try {
                                    Map<String, String> params = new HashMap<String, String>();
                                    Map<String, String> headers = new HashMap<String, String>();

                                    int cur = 0;

                                    for (int i = 0; i < invitations.length(); i++) {
                                        JSONObject group = invitations.getJSONObject(i);
                                        JSONArray users = group.getJSONArray("CPUsers");
                                        for (int j = 0; j < users.length(); j++) {
                                            if (cur == position) {
                                                groupId = group.getString("id");
                                                JSONObject user = users.getJSONObject(j);
                                                userId = user.getString("id");
                                            }
                                        }
                                    }
                                    params.put("cpgroupid", groupId);
                                    params.put("userResponse", "true");
                                    if (isDriver) {
                                        params.put("cpuserid", userId);
                                    } else {
                                        params.put("cpuserid", currentId);
                                    }

                                    headers.put("x-access-token", token);
                                    request.makePostRequest(GlobalConstants.UPDATE_INVITE, params, null, null, headers);
                                } catch (JSONException e) {
                                    Log.d("afadf", "an error occurred setting up accept button.");
                                }
                            }
                        });

                        declineButton.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // Make the network request to decline this invitation.
                                String userId = "";
                                String groupId = "";
                                try {
                                    Map<String, String> params = new HashMap<String, String>();
                                    Map<String, String> headers = new HashMap<String, String>();

                                    int cur = 0;

                                    for (int i = 0; i < invitations.length(); i++) {
                                        JSONObject group = invitations.getJSONObject(i);
                                        JSONArray users = group.getJSONArray("CPUsers");
                                        for (int j = 0; j < users.length(); j++) {
                                            if (cur == position) {
                                                groupId = group.getString("id");
                                                JSONObject user = users.getJSONObject(j);
                                                userId = user.getString("id");
                                            }
                                        }
                                    }
                                    params.put("cpgroupid", groupId);
                                    params.put("userResponse", "false");

                                    if (isDriver) {
                                        params.put("cpuserid", userId);
                                    } else {
                                        params.put("cpuserid", currentId);
                                    }

                                    headers.put("x-access-token", token);
                                    request.makePostRequest(GlobalConstants.UPDATE_INVITE, params, successCallback, failure, headers);
                                } catch (JSONException e) {
                                    Log.d("afadf", "an error occurred setting up decline button.");
                                }
                            }
                        });
                    }
                }
            }
        } catch (JSONException e) {

        }

        return itemView;
    }
}
