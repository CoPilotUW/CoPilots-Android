package com.copilot.copilot.invitationlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.copilot.copilot.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by xiaozhuoyu on 2017-07-16.
 */

public class InvitationListAdapter extends BaseAdapter {
    // Declare Variables
    Context context;
    ArrayList<InvitationListItem> invitations = null;
    LayoutInflater inflater;

    public InvitationListAdapter(Context context, ArrayList<JSONObject> invitations) {
        this.context = context;
        // Convert it into InvitationListItem
        this.invitations = new ArrayList<InvitationListItem>();
        convertToInvitationListItem(invitations);
    }

    public void updateInvitations(ArrayList<JSONObject> invitations) {
        convertToInvitationListItem(invitations);
    }

    public int getCount() {
        return invitations.size();
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

        // Update each individual list item.
        name.setText(invitations.get(position).getName());
        from.setText(invitations.get(position).getStartingLocation());
        destination.setText(invitations.get(position).getDestination());
        time.setText(invitations.get(position).getTime());

        ImageButton acceptButton = (ImageButton) itemView.findViewById(R.id.invitation_accept_button);
        ImageButton declineButton = (ImageButton) itemView.findViewById(R.id.invitation_decline_button);

        acceptButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            // Make the network request to accept this invitation.
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            // Make the network request to decline this invitation.
            }
        });

        return itemView;
    }

    private void convertToInvitationListItem(ArrayList<JSONObject> invitations) {
        // First reset our current list of invitations.
        this.invitations.clear();
        for (int i = 0; i < invitations.size(); i++) {
            try {
                this.invitations.add(new InvitationListItem(
                        invitations.get(i).getString("name"),
                        invitations.get(i).getString("from"),
                        invitations.get(i).getString("destination"),
                        invitations.get(i).getString("time"),
                        invitations.get(i).getString("id")
                ));
            } catch (JSONException e) {
                Log.d("error", "Error occcurred when trying to convert InvitationListItem");
            }
        }
    }
}
