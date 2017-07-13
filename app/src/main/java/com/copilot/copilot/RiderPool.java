package com.copilot.copilot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

/**
 * Created by xiaozhuoyu on 2017-07-12.
 */

public class RiderPool extends AppCompatActivity {
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_pool);
        list = (ListView) findViewById(R.id.rider_pool_list);
    }
}
