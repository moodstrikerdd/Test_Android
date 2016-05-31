package com.moo.test.test_android.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/*
 * Toast统一管理类
 */
public class T {
    private static Context mContext;

    public T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 标准toast
     */
    public static void showShort(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(mContext, resId, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义toast 需进一步完成代码
     */
    public static void showCustom() {
        int layoutId = 0;
        Toast toast = new Toast(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layoutId, null);
        toast.setView(view);
        toast.setDuration(1000);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
