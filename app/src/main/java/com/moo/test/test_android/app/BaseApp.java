package com.moo.test.test_android.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BaseApp extends Application {
    public static BaseApp mBaseApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApp = this;
        x.Ext.init(this);
    }
}
