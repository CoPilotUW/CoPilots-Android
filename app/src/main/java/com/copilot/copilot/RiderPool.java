package com.copilot.copilot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by xiaozhuoyu on 2017-07-12.
 */

public class RiderPool extends AppCompatActivity {
    String[] riderJsons = {"Akash", "Jameson", "Addy", "Jobair", "Surudth"};
    ListView list;
    RiderPoolListAdapter adapter;
    Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_pool);
        list = (ListView) findViewById(R.id.rider_pool_list);

        backButton = (Button)findViewById(R.id.rider_pool_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderPool.super.onBackPressed();
            }
        });

        // Pass results to ListViewAdapter Class
        adapter = new RiderPoolListAdapter(this, riderJsons);
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
}
