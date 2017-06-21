package com.copilot.copilot;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

public class TripDetailsMembersFragment extends Fragment {
    String[] rank;
    String[] country;
    String[] population;
    ListView list;
    TripDetailMemberListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_details_member_fragment, container,
                false);
        // Generate sample data
        rank = new String[] { "1", "2", "3", "4", "5" };

        country = new String[] { "China", "India", "United States",
                "Indonesia", "Brazil" };

        population = new String[] { "1,354,040,000", "1,210,193,422",
                "315,761,000", "237,641,326", "193,946,886" };


        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.trip_details_member_fragment_list);

        // Pass results to ListViewAdapter Class
        adapter = new TripDetailMemberListAdapter(getContext(), rank, country, population);
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
}
