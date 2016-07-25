package com.moo.mvpdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * name:BaseTypeCustomAdapter
 * author:moo.
 * date:2016/7/15.
 * instruction:
 */
public abstract class BaseTypeCustomAdapter<T> extends BaseAdapter {
    public List<T> data;
    public Context context;


    public BaseTypeCustomAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public T getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }


    @Override
    public int getViewTypeCount() {
        return getTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context, position, convertView, parent, getLayoutId(position, getItemType(position)));
        bindData(viewHolder, position);
        return null;
    }

    /**
     * 获取每一项的type类型
     */
    public abstract int getItemType(int position);

    /**
     * 获取type的总数
     */
    public abstract int getTypeCount();

    /**
     * 获取相应type对应的layoutId
     */
    public abstract int getLayoutId(int position, int type);

    /**
     * 绑定数据
     */
    protected abstract void bindData(ViewHolder viewHolder, int position);

}
