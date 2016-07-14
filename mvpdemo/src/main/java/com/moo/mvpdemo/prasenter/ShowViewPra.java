package com.moo.mvpdemo.prasenter;

import android.os.Handler;

import com.moo.mvpdemo.modle.ShowViewM;
import com.moo.mvpdemo.modle.bean.User;
import com.moo.mvpdemo.modle.IShowViewM;
import com.moo.mvpdemo.modle.IShowViewMResult;
import com.moo.mvpdemo.view.IShowView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class ShowViewPra extends BasePresenter<IShowView> {
    private IShowViewM iShowViewM = new ShowViewM();

    private Handler handler = new Handler();

    public void bindData() {
        getReference().loading();
        iShowViewM.loadData(new IShowViewMResult() {
            @Override
            public void bindData(final List<User> result) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getReference().show(result);
                    }
                });
            }
        });
    }
}
