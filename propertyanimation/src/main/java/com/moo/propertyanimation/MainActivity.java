package com.moo.propertyanimation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView ivAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivAnimator = (ImageView) findViewById(R.id.iv_main_anim);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_main_menu1:
                linearAnim();
                break;
            case R.id.iv_main_anim:
                Toast.makeText(MainActivity.this, "I'm here!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 水平运动
     */
    private void linearAnim() {
        int[] location = new int[2];
        ivAnimator.getLocationInWindow(location);
        final ValueAnimator anim = ValueAnimator.ofFloat(location[0], location[0] + 300);
        anim.setTarget(ivAnimator);
        anim.setDuration(1000).start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ivAnimator.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
    }
}
