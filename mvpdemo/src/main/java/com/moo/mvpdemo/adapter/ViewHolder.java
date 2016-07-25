package com.moo.mvpdemo.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * name:ViewHolder
 * author:moo.
 * date:2016/7/15.
 * instruction:通用ViewHolder
 */
public class ViewHolder {
    private SparseArrayCompat<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
    private int mLayoutId;
    private ViewHolder holder;

    public ViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArrayCompat<>();
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, int position, View convertView,
                                 ViewGroup parent, int layoutId) {
        ViewHolder holder;
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            holder = new ViewHolder(context, itemView, parent, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mPosition = position;
        holder.mLayoutId = layoutId;
        return holder;
    }

    public <T extends View> T getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mConvertView.findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public void setText(int id, String msg) {
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(msg);
        }
    }

    public void setText(int id, int resId) {
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(resId);
        }
    }

    public void setImageResource(int id, int resId) {
        View view = getView(id);
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
        }
    }
}

