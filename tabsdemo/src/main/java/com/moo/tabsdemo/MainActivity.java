package com.moo.tabsdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.moo.tabsdemo.adapter.MainTabsAdapter;
import com.moo.tabsdemo.fragment.AFragment;
import com.moo.tabsdemo.fragment.BFragment;
import com.moo.tabsdemo.fragment.CFragment;
import com.moo.tabsdemo.fragment.DFragment;
import com.moo.tabsdemo.utils.FragmentChangeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgMainTabs;
    private ViewPager vpMainFragments;

    private AFragment aFragment;
    private BFragment bFragment;
    private CFragment cFragment;
    private DFragment dFragment;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        ((RadioButton) rgMainTabs.getChildAt(getIntent().getIntExtra("index", 0))).setChecked(true);
    }

    private void initView() {
        rgMainTabs = (RadioGroup) findViewById(R.id.rg_main_tabs);
        vpMainFragments = (ViewPager) findViewById(R.id.vp_main_fragments);

        rgMainTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgMainTabs.getChildCount(); i++) {
                    if (checkedId == rgMainTabs.getChildAt(i).getId()) {
                        vpMainFragments.setCurrentItem(i);
                    }
                }
            }
        });

        vpMainFragments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                for (int i = 0; i < rgMainTabs.getChildCount(); i++) {
//                    ((RadioButton) rgMainTabs.getChildAt(i)).setChecked(false);
//                }
                ((RadioButton) rgMainTabs.getChildAt(position)).setChecked(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vpMainFragments.setAdapter(new MainTabsAdapter(getSupportFragmentManager(), fragmentList));
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        aFragment = AFragment.newInstance("", "");
        bFragment = BFragment.newInstance("", "");
        cFragment = CFragment.newInstance("", "");
        dFragment = DFragment.newInstance("", "");
        fragmentList.add(aFragment);
        fragmentList.add(bFragment);
        fragmentList.add(cFragment);
        fragmentList.add(dFragment);


//        new FragmentChangeUtils(rgMainTabs, R.id.fl_main_fragments, getSupportFragmentManager(), fragmentList).bind();
    }

    public static void intentStart(Activity activity, int index) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("index", index);
        activity.startActivity(intent);
    }
}
