package com.copilot.copilot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.helper.CPUtility;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaozhuoyu on 2017-07-12.
 */

public class RiderPool extends AppCompatActivity {
    ListView list;
    RiderPoolListAdapter adapter;
    Button backButton;
    private HTTPRequestWrapper request;
    Context currentContext;
    private String groupID;

    final VolleyCallback successCallback = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            // The response here will be an json array.
            JSONArray jsonResponse = new JSONArray();
            try {
                jsonResponse = new JSONArray(response);
            } catch (JSONException e) {

            }

            list = (ListView) findViewById(R.id.rider_pool_list);

            backButton = (Button)findViewById(R.id.rider_pool_back_button);

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RiderPool.super.onBackPressed();
                }
            });

            // Pass results to ListViewAdapter Class
            adapter = new RiderPoolListAdapter(currentContext, jsonResponse, groupID);
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
    };

    final VolleyCallback failure = new VolleyCallback() {
        @Override
        public void onSuccessResponse(String response) {
            Toast.makeText(getApplicationContext(), "Could not create the trip :( try again later! " + response, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_pool);

        groupID = getIntent().getStringExtra("cpgroupid").toString();
        currentContext = this;

        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, RiderPool.this);

        setupAdapter();

    }

    private void setupAdapter() {

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

        headers.put("x-access-token", accessToken);

        request.makeGetRequest(GlobalConstants.GET_TRIP_SEARCHES, params, successCallback, failure, headers);
    }
}
