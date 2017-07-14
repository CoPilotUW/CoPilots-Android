package com.copilot.copilot;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.toolbox.Volley;
import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
         * TODO: THIS IS HOW A REQUEST CAN BE MADE USING THE VOLLEY LIBRARY
         *
        final VolleyCallback failureCallback = new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                // Handle Failure
            }
        };

        final VolleyCallback successCallback = new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                // Handle Success
            }
        };

        final HTTPRequestWrapper requestWrapper = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL, this);

        final HashMap <String, String> params = new HashMap<>();
        params.put("email", email);

        requestWrapper.makePostRequest(GlobalConstants.AUTH_ENDPOINT, params, successCallback, failureCallback);
        */
    }
}
