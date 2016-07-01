package com.example.sidesliplist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private List<Integer> lists = new ArrayList<>();
    private MyRecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initviews();
    }

    private void initviews() {
        recyclerView = (MyRecyclerView) findViewById(R.id.my_recycler);
        adapter = new RecyclerAdapter(getApplicationContext(), lists);
        adapter.setOnMenuClickListener(new RecyclerAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(View view, int position) {

                switch (view.getId()) {
                    case R.id.tv_xxx_item_menu1:
                        Integer integer = lists.get(position);
                        lists.remove(position);
                        lists.add(0, integer);
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_xxx_item_menu2:
                        Toast.makeText(MainActivity.this, "标记已读！", Toast.LENGTH_SHORT).show();
                        recyclerView.hideMenu();//将菜单回弹
                        break;
                    case R.id.tv_xxx_item_menu3:
                        lists.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

        });
        manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        for (int i = 1; i < 20; i++) {
            lists.add(i);
        }
    }


}
