package com.moo.changegridviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ChangeAdapter extends BaseAdapter {
    public static final int MODE_GRID = 0;
    public static final int MODE_LIST = 1;

    public int mode = 0;
    private List<String> data;
    private LayoutInflater inflater;

    public ChangeAdapter(Context context, List<String> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    public void setMode(int mode) {
        this.mode = mode;
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
    public int getItemViewType(int position) {
        return mode;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int mode = getItemViewType(position);
        if (convertView == null) {
            convertView = inflater.inflate(mode == MODE_GRID ? R.layout.list_item_main_grid : R.layout.list_item_main_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(data.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textView;

        public ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.item_text);
        }
    }
}
