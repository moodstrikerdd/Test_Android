package com.moo.mvpdemo.prasenter;


import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/7/14.
 */
public class BasePresenter<V> extends AppCompatActivity {
    private WeakReference<V> reference;

    public void attach(V v) {
        reference = new WeakReference<>(v);
    }

    public void dettach() {
        if (reference != null) {
            reference.clear();
            reference = null;
        }
    }

    public V getReference() {
        return reference == null ? null : reference.get();
    }
}
