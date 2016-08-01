package com.moo.test.test_android.app;

import android.app.Application;

import com.moo.test.test_android.utils.T;

import org.xutils.x;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BaseApp extends Application {
    public static BaseApp mBaseApp;

    public static BaseApp getBaseApp() {
        return mBaseApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApp = this;
        x.Ext.init(this);
        T.init(this);
    }
}
