package com.copilot.copilot;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailsMembersFragment extends Fragment {
    // Hard coded json for trip member.
    String[] riderJsons = {"Akash", "Jameson", "Addy", "Jobair", "Surudth"};
    ListView list;
    TripDetailMemberListAdapter adapter;
    Button showRiderPool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_details_member_fragment, container,
                false);

        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.trip_details_member_fragment_list);

        showRiderPool = new Button(this.getContext());
        showRiderPool.setText("Show Riders!");
        showRiderPool.setBackgroundColor(getResources().getColor(R.color.editTextColor));
        
        list.addFooterView(showRiderPool);

        showRiderPool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRiderPool(v);
            }
        });

        // Pass results to ListViewAdapter Class
        adapter = new TripDetailMemberListAdapter(getContext(), riderJsons);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture clicks on ListView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }

        });
        return rootView;
    }


    public void showRiderPool(View view)
    {
        Intent riderPoolIntent = new Intent(this.getActivity(), RiderPool.class);
        startActivity(riderPoolIntent);
    }
}
