package com.moo.tabsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

public class EnterActivity extends AppCompatActivity {

    private RadioGroup rgEnterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        rgEnterIndex = (RadioGroup) findViewById(R.id.rg_enter_index);

    }

    public void jump(View view) {
        for (int i = 0; i < rgEnterIndex.getChildCount(); i++) {
            if (rgEnterIndex.getCheckedRadioButtonId() == rgEnterIndex.getChildAt(i).getId()) {
                MainActivity.intentStart(EnterActivity.this, i);
            }
        }
    }
}
