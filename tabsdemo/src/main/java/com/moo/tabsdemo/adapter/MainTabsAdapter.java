package com.moo.tabsdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * name:MainTabsAdapter
 * author:moo.
 * date:2016/8/1.
 * instruction:
 */
public class MainTabsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MainTabsAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
