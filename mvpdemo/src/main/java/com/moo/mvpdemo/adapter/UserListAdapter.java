package com.moo.mvpdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moo.mvpdemo.R;
import com.moo.mvpdemo.modle.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> data;

    public UserListAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
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
        ViewHolder viewHolder = ViewHolder.get(context,position,convertView,parent,R.layout.list_item_user);
        ((TextView)viewHolder.getView(R.id.tv_item_user_username)).setText(data.get(position).getUserName());
        ((TextView)viewHolder.getView(R.id.tv_item_user_tel)).setText(data.get(position).getUserTel());
        return viewHolder.getConvertView();
    }
}
