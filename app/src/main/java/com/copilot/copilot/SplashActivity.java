package com.copilot.copilot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.copilot.auth.FacebookAuthActivity;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;

/**
 * Created by Akash on 2017-05-12.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(SplashActivity.this, FacebookAuthActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }
}
