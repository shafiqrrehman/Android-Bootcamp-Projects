package com.aziz.swipetabsdemo.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aziz.swipetabsdemo.activity.fragments.AFragment;
import com.aziz.swipetabsdemo.activity.fragments.BFragment;
import com.aziz.swipetabsdemo.activity.fragments.CFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {

            case 0:
                return new AFragment();

            case 1:
                return new BFragment();

            case 2:
                return new CFragment();
        }

        return new AFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
