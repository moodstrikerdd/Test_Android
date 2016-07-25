package com.moo.mvpdemo.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;

import com.moo.mvpdemo.R;
import com.moo.mvpdemo.adapter.UserListAdapter;
import com.moo.mvpdemo.modle.bean.User;
import com.moo.mvpdemo.prasenter.BasePresenter;
import com.moo.mvpdemo.prasenter.ShowViewPra;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements IShowView {

    private ProgressDialog progressDialog;
    private ListView lvMainContent;

    private UserListAdapter adapter;
    private List<User> data;
    private ShowViewPra showViewPra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    public BasePresenter createPresenter() {
        showViewPra = new ShowViewPra();
        return showViewPra;
    }

    private void initData() {
        showViewPra.bindData();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据请求中...");
        lvMainContent = (ListView) findViewById(R.id.lv_main_content);
        data = new ArrayList<>();
        adapter = new UserListAdapter(this, data, R.layout.list_item_user);
        lvMainContent.setAdapter(adapter);
    }


    @Override
    public void loading() {
        progressDialog.show();
    }

    @Override
    public void show(List<User> data) {
        this.data.clear();
        this.data.addAll(data);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }
}
