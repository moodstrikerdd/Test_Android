package com.moo.flowviewgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moo.flowviewgroup.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = (FlowLayout) findViewById(R.id.fl_main_content);
        List<View> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TextView textView = new TextView(this);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 5, 10);
            textView.setPadding(10, 5, 5, 10);
            textView.setLayoutParams(params);
            textView.setBackgroundResource(R.drawable.shape_text_item);
            textView.setText(i + "");
            textView.setClickable(true);
            if (i > 10) {
                textView.setVisibility(View.GONE);
            }
            list.add(textView);
        }

        flowLayout.setViews(list);
        flowLayout.setOnItemClickListener(new FlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int Position) {
                Toast.makeText(MainActivity.this, "position" + Position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
