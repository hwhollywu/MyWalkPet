package com.hwhollywu.mobileproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hwhollywu on 7/3/16.
 */

public class MyPagerAdapter  extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return new PetFragment();
            case 1:
                return new MapsFragment();
            default:
                return new PetFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Pet";
            case 1:
                return "Walk";
            default:
                return "unknown";
        }
    }
}

