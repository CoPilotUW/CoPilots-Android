package com.copilot.copilot;

import android.hardware.camera2.params.Face;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.Toast;

import com.copilot.copilot.auth.FacebookAuthActivity;
import com.copilot.copilot.invitationlist.InvitationList;
import com.copilot.helper.VolleyCallback;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class RoleActivity extends AppCompatActivity {

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

        final LoginButton logoutButton = (LoginButton) findViewById(R.id.logout_button);
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
        };
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
