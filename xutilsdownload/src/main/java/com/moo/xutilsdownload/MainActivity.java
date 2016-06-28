package com.moo.xutilsdownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.pb_main_progress)
    private ProgressBar pbProgress;
    @ViewInject(R.id.btn_main_download)
    private Button btnDownload;

    private Callback.Cancelable cancelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelable == null || cancelable.isCancelled()) {
                    initData();
                } else {
                    cancelable.cancel();
                }
            }
        });
    }

    private void initData() {
        String url = "http://img.ivsky.com/img/bizhi/pre/201606/24/the_legend_of_the_condor_heroes.jpg";
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(this.getExternalFilesDir("file").getPath());
        cancelable = x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                pbProgress.setProgress((int) (current * 100 / total));
            }

            @Override
            public void onSuccess(File result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
