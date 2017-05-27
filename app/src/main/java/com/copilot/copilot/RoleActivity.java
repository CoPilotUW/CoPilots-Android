package com.copilot.copilot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;

public class RoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
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
