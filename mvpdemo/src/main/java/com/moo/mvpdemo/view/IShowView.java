package com.moo.mvpdemo.view;

import com.moo.mvpdemo.modle.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface IShowView {
    void loading();

    void show(List<User> data);
}
