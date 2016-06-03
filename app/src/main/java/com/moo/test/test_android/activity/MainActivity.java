package com.moo.test.test_android.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moo.test.test_android.R;
import com.moo.test.test_android.adapter.ViewHolder;
import com.moo.test.test_android.adapter.recyclerview.CommonAdapter;
import com.moo.test.test_android.adapter.recyclerview.OnItemClickListener;
import com.moo.test.test_android.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.cdl_main_content)
    private CoordinatorLayout cdlMainContent;
    @ViewInject(R.id.ctb_main_title)
    private CollapsingToolbarLayout ctbMainTitle;
    @ViewInject(R.id.rv_main_content)
    private RecyclerView rvMainContent;

    private List<TextViewInfo> data;
    private CommonAdapter<TextViewInfo> adapter;
    private int[] colors = {R.drawable.shape_textview_main_content, R.drawable.shape_textview_main_content1, R.drawable.shape_textview_main_content2, R.drawable.shape_textview_main_content3};

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    public void initView() {
        final DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        ctbMainTitle.setTitle(displayMetrics.widthPixels + "----" + displayMetrics.heightPixels);
        data = new ArrayList<>();
//        rvMainContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMainContent.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        rvMainContent.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        adapter = new CommonAdapter<TextViewInfo>(MainActivity.this, android.R.layout.simple_list_item_1, data) {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void convert(ViewHolder holder, TextViewInfo textViewInfo) {
                TextView textView = holder.getView(android.R.id.text1);
                textView.setBackgroundResource(colors[new Random().nextInt(4)]);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5, 5, 5, 5);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(layoutParams);
                textView.setHeight(textViewInfo.getHeight());
                textView.setText(textViewInfo.getInfo());
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, final int position) {
                Snackbar snackbar = Snackbar.make(cdlMainContent, data.get(position).getInfo(), Snackbar.LENGTH_LONG);
                snackbar.setAction("go", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        T.showLong(data.get(position).getInfo());
                    }
                });
                View view1 = snackbar.getView();
                view1.setBackgroundResource(R.color.colorLightBlue);
                snackbar.show();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
        rvMainContent.setItemAnimator(new DefaultItemAnimator());
        rvMainContent.setAdapter(adapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i <= 40; i++) {
            data.add(new TextViewInfo("Item===>" + (i + 1), 50 * (new Random().nextInt(5) + 1)));
        }
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    class TextViewInfo {
        private String info;
        private int height;

        public TextViewInfo(String info, int height) {
            this.info = info;
            this.height = height;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
