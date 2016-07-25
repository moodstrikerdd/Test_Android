package com.moo.toptab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private RelativeLayout rlMainTop;
    private ListView lvMainContent;

    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        rlMainTop = (RelativeLayout) findViewById(R.id.tl_main_top);
        lvMainContent = (ListView) findViewById(R.id.lv_main_content);

        final View headView = new View(this);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 300);
        headView.setLayoutParams(layoutParams);
        lvMainContent.addHeaderView(headView);

        for (int i = 0; i < 20; i++) {
            data.add("item===>" + i);
        }
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        lvMainContent.setAdapter(adapter);


        final ListAdapter listAdapter = lvMainContent.getAdapter();
        View itemView = listAdapter.getView(0, null, null);
        final int height = itemView.getMeasuredHeight();
        Log.d(TAG, height + "");


        lvMainContent.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int[] rlMainLocation = new int[2];
                int[] headViewLocation = new int[2];
                int[] headViewLocationScr = new int[2];

//                Log.d(TAG, headView.getBottom() + "");

                rlMainTop.getLocationInWindow(rlMainLocation);
                headView.getLocationInWindow(headViewLocation);
                headView.getLocationOnScreen(headViewLocationScr);
                Log.d(TAG, "Location===>" + headViewLocationScr[1] + "           " + headViewLocation[1] + "    " + headView.getBottom());
            }
        });

        lvMainContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

//        lvMainContent.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY <= height) {
//                    float alpha = (float) scrollY / (float) height;
//                    rlMainTop.setAlpha(alpha);
//                } else {
//                    Toast.makeText(MainActivity.this, "显示内容", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

    }


}
