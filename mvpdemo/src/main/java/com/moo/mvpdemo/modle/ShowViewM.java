package com.moo.mvpdemo.modle;

import com.moo.mvpdemo.modle.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class ShowViewM implements IShowViewM {
    @Override
    public void loadData(final IShowViewMResult result) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> data = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    data.add(new User("userName" + i, "tel:1801111111" + i));
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    result.bindData(data);
                }
            }
        }).start();
    }
}
