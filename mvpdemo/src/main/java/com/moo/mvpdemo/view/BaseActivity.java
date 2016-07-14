package com.moo.mvpdemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.moo.mvpdemo.prasenter.BasePresenter;

/**
 * Created by Administrator on 2016/7/14.
 */
public abstract class BaseActivity extends AppCompatActivity {

    BasePresenter basePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basePresenter = createPresenter();
        basePresenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        basePresenter.dettach();
    }

    public abstract BasePresenter createPresenter();

}
