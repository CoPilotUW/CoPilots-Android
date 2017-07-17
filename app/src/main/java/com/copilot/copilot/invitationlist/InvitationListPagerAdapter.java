package com.copilot.copilot.invitationlist;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by xiaozhuoyu on 2017-07-16.
 */

public class InvitationListPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public InvitationListPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                InvitationListRiderRequestsFragment tab1 = new InvitationListRiderRequestsFragment();
                return tab1;
            case 1:
                InvitationListDriverInvitesFragment tab2 = new InvitationListDriverInvitesFragment();
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
