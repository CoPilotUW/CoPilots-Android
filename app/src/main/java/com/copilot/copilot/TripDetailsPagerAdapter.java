package com.copilot.copilot;

import android.os.Bundle;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import com.copilot.helper.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TripDetailsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String groupId;
    JSONObject tripDetails;

    public TripDetailsPagerAdapter(FragmentManager fm, int NumOfTabs, String groupId) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.groupId = groupId;
    }

    @Override
    public Fragment getItem(int position) {
        // Make the api call to fetch the result
        Bundle args = new Bundle();
        args.putString("cpgroupid", this.groupId);

        switch (position) {
            case 0:
                TripDetailsTripFragment tab1 = new TripDetailsTripFragment();
                args.putString("cpgroupid", this.groupId);
                tab1.setArguments(args);
                return tab1;
            case 1:
                TripDetailsMembersFragment tab2 = new TripDetailsMembersFragment();
                tab2.setArguments(args);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}