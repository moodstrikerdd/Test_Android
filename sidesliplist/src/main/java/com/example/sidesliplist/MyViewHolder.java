package com.example.sidesliplist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tangyangkai on 16/6/12.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    boolean isShowing;

    TextView tvName;
    LinearLayout llMenu;
    TextView tvMenu1;
    TextView tvMenu2;
    TextView tvMenu3;

    public MyViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_xxx_item_name);
        llMenu = (LinearLayout) itemView.findViewById(R.id.ll_xxx_item_menu);
        tvMenu1 = (TextView) itemView.findViewById(R.id.tv_xxx_item_menu1);
        tvMenu2 = (TextView) itemView.findViewById(R.id.tv_xxx_item_menu2);
        tvMenu3 = (TextView) itemView.findViewById(R.id.tv_xxx_item_menu3);
    }
}
