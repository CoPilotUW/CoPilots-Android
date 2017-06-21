package com.copilot.copilot;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by xiaozhuoyu on 2017-06-20.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TripDetailsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    ArrayList<String> bookingDetails;

    public TripDetailsPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> detailsFromBooking) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TripDetailsTripFragment tab1 = new TripDetailsTripFragment(bookingDetails);
                return tab1;
            case 1:
                TripDetailsMembersFragment tab2 = new TripDetailsMembersFragment();
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