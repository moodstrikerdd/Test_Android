package com.moo.propertyanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private FrameLayout flMain;
    private LinearLayout llLogin;
    private LinearLayout llRegister;

    private ValueAnimator animatorFirst;
    private ValueAnimator animatorSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        flMain = (FrameLayout) findViewById(R.id.fl_login_main);
        llLogin = (LinearLayout) findViewById(R.id.ll_login_login);
        llRegister = (LinearLayout) findViewById(R.id.ll_login_register);
    }


    /**
     * 初始化数据
     */
    private void initData() {
        ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotationY = (Float) animation.getAnimatedValue();
                Log.d("rotationY", "---" + rotationY);
                flMain.setRotationY(rotationY);
            }
        };
        AnimatorListenerAdapter animatorListenerAdapterLogin = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSecond.start();
                showReverse();
            }
        };
        AnimatorListenerAdapter animatorListenerAdapterRegister = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        };

        animatorSecond = ValueAnimator.ofFloat(-270, -360);
        animatorSecond.setTarget(flMain);
        animatorSecond.setDuration(500);
        animatorSecond.addUpdateListener(updateListener);
        animatorSecond.addListener(animatorListenerAdapterRegister);

        animatorFirst = ValueAnimator.ofFloat(0, -90);
        animatorFirst.setTarget(flMain);
        animatorFirst.setDuration(500);
        animatorFirst.addUpdateListener(updateListener);
        animatorFirst.addListener(animatorListenerAdapterLogin);

    }

    /**
     * 显示反转
     */
    private void showReverse() {
        int visibility = llLogin.getVisibility();
        llLogin.setVisibility(llRegister.getVisibility());
        llRegister.setVisibility(visibility);
    }

    public void textClick(View view) {
        if (view instanceof TextView) {
            Toast.makeText(LoginActivity.this, ((TextView) view).getText().toString().trim(), Toast.LENGTH_SHORT).show();
            animatorFirst.start();
        }
    }
}
