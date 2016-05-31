package com.moo.test.test_android.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.moo.test.test_android.R;
import com.moo.test.test_android.adapter.ViewHolder;
import com.moo.test.test_android.adapter.recyclerview.CommonAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.ctb_main_title)
    private CollapsingToolbarLayout ctbMainTitle;
    @ViewInject(R.id.rv_main_content)
    private RecyclerView rvMainContent;

    private List<String> data;
    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        ctbMainTitle.setTitle(displayMetrics.widthPixels + "----" + displayMetrics.heightPixels);
        data = new ArrayList<>();
        rvMainContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CommonAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(android.R.id.text1, s);
            }
        };
        rvMainContent.setAdapter(adapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i <= 20; i++) {
            data.add("item ======> " + (i + 1));
        }
        adapter.notifyDataSetChanged();
    }
}
