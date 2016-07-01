package com.example.sidesliplist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangyangkai on 16/6/12.
 */
public class RecyclerAdapter extends RecyclerView.Adapter {


    private Context context;
    private LayoutInflater inflater;
    private List<Integer> lists = new ArrayList<>();
    private OnMenuClickListener mOnMenuClickListener;
    private View.OnClickListener mOnClickListener;

    private int screenWidth;


    public RecyclerAdapter(Context context, List<Integer> lists) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.lists = lists;
        DisplayMetrics displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyViewHolder(inflater.inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMenuClickListener != null) {
                    mOnMenuClickListener.onMenuClick(v, position);
                }
            }
        };
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tvName.setText("第" + String.valueOf(lists.get(position)) + "项");
        viewHolder.tvMenu1.setOnClickListener(mOnClickListener);
        viewHolder.tvMenu2.setOnClickListener(mOnClickListener);
        viewHolder.tvMenu3.setOnClickListener(mOnClickListener);
        LinearLayout.LayoutParams layoutParams;
        if (position % 2 == 0) {
            viewHolder.tvMenu2.setVisibility(View.GONE);
            layoutParams = new LinearLayout.LayoutParams((int) (screenWidth * 0.5), LinearLayout.LayoutParams.MATCH_PARENT);
        } else {
            viewHolder.tvMenu2.setVisibility(View.VISIBLE);
            layoutParams = new LinearLayout.LayoutParams((int) (screenWidth * 0.6), LinearLayout.LayoutParams.MATCH_PARENT);
        }
        //这个一定要调用
        viewHolder.itemView.scrollTo(0, 0);
        viewHolder.llMenu.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        } else {
            return 0;
        }
    }

    public void setOnMenuClickListener(OnMenuClickListener OnMenuClickListener) {
        this.mOnMenuClickListener = OnMenuClickListener;
    }

    public interface OnMenuClickListener {
        void onMenuClick(View view, int postion);
    }
}
