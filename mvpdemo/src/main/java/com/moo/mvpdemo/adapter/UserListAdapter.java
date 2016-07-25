package com.moo.mvpdemo.adapter;

import android.content.Context;

import com.moo.mvpdemo.R;
import com.moo.mvpdemo.modle.bean.User;

import java.util.List;

/**
 * Created by moo on 2016/7/14.
 */
public class UserListAdapter extends BaseCustomAdapter<User> {
    public UserListAdapter(Context context, List<User> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    protected void bindData(ViewHolder viewHolder, int position) {
        viewHolder.setText(R.id.tv_item_user_username,data.get(position).getUserName());
        viewHolder.setText(R.id.tv_item_user_tel,data.get(position).getUserTel());
    }
}
