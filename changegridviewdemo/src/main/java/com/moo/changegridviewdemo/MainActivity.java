package com.moo.changegridviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private GridView mGridView;
    private ChangeAdapter adapter;
    private List<String> data;
    private int currentGridMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        data = new ArrayList<>();
        mGridView = (GridView) findViewById(R.id.gv_main_content);
        adapter = new ChangeAdapter(this, data);
        adapter.setMode(ChangeAdapter.MODE_GRID);
        mGridView.setNumColumns(3);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, data.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            data.add("item --- " + i);
        }
        adapter.notifyDataSetChanged();
    }

    public void change(View view) {
        if (currentGridMode == ChangeAdapter.MODE_GRID) {
            currentGridMode = ChangeAdapter.MODE_LIST;
            mGridView.setNumColumns(1);
            adapter.setMode(ChangeAdapter.MODE_LIST);
        } else {
            currentGridMode = ChangeAdapter.MODE_GRID;
            mGridView.setNumColumns(3);
            adapter.setMode(ChangeAdapter.MODE_GRID);
        }
        adapter.notifyDataSetChanged();
    }
}
