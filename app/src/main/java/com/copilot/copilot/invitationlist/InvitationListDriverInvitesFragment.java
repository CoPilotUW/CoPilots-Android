package com.copilot.copilot.invitationlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.copilot.copilot.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by xiaozhuoyu on 2017-07-16.
 */

public class InvitationListDriverInvitesFragment extends Fragment{
    ListView list;
    InvitationListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.invitation_list_fragment, container,
                false);

        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.invitation_fragment_list);

        ArrayList<JSONObject> invites = new ArrayList<JSONObject>();
        try {
            for(int i = 0; i < InvitationJson.invites.length; i++) {
                invites.add(new JSONObject(InvitationJson.invites[i]));
            }
        } catch (JSONException e) {
            Log.d("earerer", "we got an error parsing the json crap in invitation");
        }

        // Pass results to ListViewAdapter Class
        adapter = new InvitationListAdapter(getContext(), invites);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture clicks on ListView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }

        });
        return rootView;
    }
}
