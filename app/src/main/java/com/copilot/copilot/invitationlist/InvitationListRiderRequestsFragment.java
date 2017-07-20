package com.copilot.copilot.invitationlist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.copilot.R;
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

public class InvitationListRiderRequestsFragment extends Fragment{
    ListView list;
    InvitationListAdapter adapter;
    HTTPRequestWrapper request;
    JSONArray invites;
    View rootView;


    final VolleyCallback successCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            // Parse the json response that we get back.
            Log.d("asdfasf", response);
            try {
                invites = new JSONArray(response);
                setup();
            } catch (JSONException e) {
                invites = new JSONArray();
            }

            setup();
        }
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Toast.makeText(getContext(), "Could not fetch the invites :( try again later! " + response, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, getContext());
        invites = new JSONArray();
        getRiderRequests();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.invitation_list_fragment, container,
                false);

        return rootView;
    }

    private void setup() {
        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.invitation_fragment_list);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");
        String id = sharedPref.getString(GlobalConstants.USER_ID, "");

        // Pass results to ListViewAdapter Class
        adapter = new InvitationListAdapter(getContext(), invites, true, request, accessToken, id);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture clicks on ListView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }

        });
    }

    private void getRiderRequests() {
        Map<String, String> headers = new HashMap<String, String>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

        headers.put("x-access-token", accessToken);

        request.makeGetRequest(GlobalConstants.PENDING_REQUESTS, null, successCallback, failure, headers);
    }

}
