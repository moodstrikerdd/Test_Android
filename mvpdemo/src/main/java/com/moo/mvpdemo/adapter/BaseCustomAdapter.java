package com.moo.mvpdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * name:BaseCustomAdapter
 * author:moo.
 * date:2016/7/15.
 * instruction: 简化BaseAdapter基类
 */
public abstract class BaseCustomAdapter<T> extends BaseAdapter {
    public List<T> data;
    public Context context;
    public int layoutId;

    public BaseCustomAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, position, convertView, parent, layoutId);
        bindData(viewHolder, position);
        return viewHolder.getConvertView();
    }

    protected abstract void bindData(ViewHolder viewHolder, int position);
}

