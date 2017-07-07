package com.copilot.copilot.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.copilot.com.copilot.global.GlobalConstants;
import com.copilot.copilot.R;
import com.copilot.copilot.RoleActivity;
import com.copilot.copilot.SplashActivity;
import com.copilot.helper.HTTPRequestWrapper;
import com.copilot.helper.VolleyCallback;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akash on 2017-06-30.
 */

public class FacebookAuthActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private HTTPRequestWrapper request;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_auth);

        callbackManager = CallbackManager.Factory.create();

        request = new HTTPRequestWrapper(GlobalConstants.GLOBAL_URL, FacebookAuthActivity.this);

        final VolleyCallback successCallback = new VolleyCallback() {
            @Override
            public void onSuccessResponse(String token) {
                // Save the JWT token to session
                SharedPreferences sharedPref = getSharedPreferences(GlobalConstants.APP_SESSION, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("x-access-token", token);
                editor.commit();

                Intent startApp = new Intent(FacebookAuthActivity.this, RoleActivity.class);
                startActivity(startApp);
                finish();
            }
        };

        final VolleyCallback failure = new VolleyCallback() {
            @Override
            public void onSuccessResponse(String response) {
                Toast.makeText(getApplicationContext(), "Login Failed: " + response, Toast.LENGTH_SHORT).show();
            }
        };

        AccessToken fbAt = AccessToken.getCurrentAccessToken();

        if (fbAt != null) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", fbAt.getToken());

            request.makeGetRequest(GlobalConstants.AUTH_ENDPOINT, params, successCallback, failure, null);
        }

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Login to the app
                String accessToken = loginResult.getAccessToken().getToken();


                Map<String, String> params = new HashMap<String, String>();
                params.put("access_token", loginResult.getAccessToken().getToken());

                request.makeGetRequest(GlobalConstants.AUTH_ENDPOINT, params, successCallback, failure, null);

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "CANCELLED", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
