package com.moo.test.test_android.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.moo.test.test_android.utils.ActivityTaskManager;

import org.xutils.x;

/**
 * Created by Administrator on 2016/5/30.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Typeface typeface;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        try {
//            layoutInflater = LayoutInflater.from(this);
//            LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
//                @Override
//                public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//                    View view;
//                    if (typeface == null) {
//                        typeface = Typeface.createFromAsset(getAssets(), "pop.ttf");
//                    }
//
//                    AppCompatDelegate delegate = getDelegate();
//                    view = delegate.createView(parent, name, context, attrs);
//
//                    if (view != null && view instanceof TextView) {
//                        ((TextView) view).setTypeface(typeface);
//                    }
//                    return view;
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        ActivityTaskManager.getActivityTaskManager().pushActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityTaskManager.getActivityTaskManager().popActivity();
    }

    public abstract void initView();

    public abstract void initData();

}
