package com.moo.test.test_android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.moo.test.test_android.utils.ActivityTaskManager;

import org.xutils.x;

/**
 * Created by Administrator on 2016/5/30.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
