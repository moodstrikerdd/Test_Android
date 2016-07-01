package com.moo.xutilsdownload.application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/6/29.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
