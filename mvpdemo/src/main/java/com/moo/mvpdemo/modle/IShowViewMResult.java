package com.moo.mvpdemo.modle;

import com.moo.mvpdemo.modle.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public interface IShowViewMResult {
    void bindData(List<User> result);
}
