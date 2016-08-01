package com.moo.tabsdemo.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import java.util.List;

/**
 * name:FragmentChangeUtils
 * author:moo.
 * date:2016/8/1.
 * instruction:
 */
public class FragmentChangeUtils implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    private int layoutId;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private List<Fragment> fragments;

    public FragmentChangeUtils(RadioGroup radioGroup, int layoutId, FragmentManager fm, List<Fragment> fragments) {
        this.radioGroup = radioGroup;
        this.layoutId = layoutId;
        this.fm = fm;
        this.fragments = fragments;
    }

    public void bind() {
        radioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.e("TAB", checkedId + "");
        for (int i = 0; i < group.getChildCount(); i++) {
            Fragment fragment = fragments.get(i);
            if (i == checkedId - 1) {
                if (fragment.isAdded()) {
                    getTransaction().show(fragment).commit();
                } else {
                    getTransaction().add(layoutId, fragment, fragment.getClass().getCanonicalName()).commit();
                }
            } else {
                if (fragment.isAdded() && !fragment.isHidden()) {
                    getTransaction().hide(fragment).commit();
                }
            }
        }
    }

    private FragmentTransaction getTransaction() {
        return fm.beginTransaction();
    }
}
