package com.copilot.copilot;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.params.Face;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.copilot.auth.FacebookAuthActivity;
import com.copilot.copilot.invitationlist.InvitationList;
import com.copilot.copilot.tripsearch.RoleViewListAdapter;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class RoleActivity extends AppCompatActivity {

    ListView list;
    RoleViewListAdapter adapter;
    Button backButton;
    private HTTPRequestWrapper request;
    Context currentContext;

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

            // Pass results to ListViewAdapter Class
            adapter = new RoleViewListAdapter(currentContext, jsonResponse);
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
            Toast.makeText(getApplicationContext(), "Failure " + response, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        View toolbarView = findViewById(R.id.toolbar);
        ImageButton messageButton = (ImageButton)toolbarView.findViewById(R.id.toolbar_message_button);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), InvitationList.class);
                startActivity(i);
            }
        });

        currentContext = this;

        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL + GlobalConstants.V1_FEATURES, RoleActivity.this);

        setupAdapter();

        /*final LoginButton logoutButton = (LoginButton) findViewById(R.id.logout_button);
        AccessTokenTracker tracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if ( currentAccessToken == null ) {
                    Toast.makeText(RoleActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                    Intent goToLogin = new Intent(RoleActivity.this, FacebookAuthActivity.class);
                    startActivity(goToLogin);
                    finish();
                }
            }
        };*/
    }

    private void setupAdapter() {

        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> params = new HashMap<String, String>();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String accessToken = sharedPref.getString(GlobalConstants.ACCESS_TOKEN, "");

        headers.put("x-access-token", accessToken);

        request.makeGetRequest(GlobalConstants.GET_TRIP_SEARCHES, params, successCallback, failure, headers);
    }

    public void clickRoleButton(View view)
    {
        Intent fireTripBooking = new Intent(this, BookingActivity.class);
        switch(view.getId())
        {
            case R.id.riderButton:
                fireTripBooking.putExtra("Role", "Rider");
                startActivity(fireTripBooking);
                break;
            case R.id.driverButton:
                fireTripBooking.putExtra("Role", "Driver");
                startActivity(fireTripBooking);
                break;
            default:
                Log.e("RoleActivity", "Invalid button clicked");
        }
    }
}
