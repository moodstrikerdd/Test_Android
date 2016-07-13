package cn.tiantianbook.www.webviewdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import de.mrapp.android.dialog.MaterialDialog;

public class MainActivity extends AppCompatActivity {
    public static final int WEB_CHOOSE_FILE = 0;
    public static final int WEB_PHOTOGRAPH = 1;

    private WebView wvMain;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback mUploadMessage;
    private Uri[] uris = new Uri[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wvMain = (WebView) findViewById(R.id.wv_main);

        wvMain.setWebViewClient(new WebViewClient() {

        });


        wvMain.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                result.confirm();
                //或者 result.cancel();
                return super.onJsAlert(view, url, message, result);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                if (mUploadMessage != null)
                    return;
                String[] items = getResources().getStringArray(R.array.select_image);
                mUploadMessage = uploadMsg;
                new MaterialDialog.Builder(MainActivity.this)
                        .setTitle("添加图片")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                {
                                    if (which == 0) {//拍照
                                        Toast.makeText(MainActivity.this, "开发中", Toast.LENGTH_SHORT).show();
                                    } else if (which == 1) {//选择照片
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");//图片文件,系统自动调用图库和文件管理
                                        intent.putExtra("outputFormat",
                                                Bitmap.CompressFormat.JPEG.toString());
                                        startActivityForResult(Intent.createChooser(intent, "选择照片"), WEB_CHOOSE_FILE);
                                    }
                                }
                            }
                        })
//                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                mUploadMessage = null;
//                                Log.i("dismissListener", "弹出框dismiss");
//                            }
//                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setWebResult();
                            }
                        })
                        .show();
            }

            // For Android < 3.0

            public void openFileChooser(ValueCallback uploadMsg) {
                openFileChooser(uploadMsg, "");
            }

            // For Android > 4.1.1
            public void openFileChooser(ValueCallback uploadMsg, String acceptType,
                                        String capture) {
                openFileChooser(uploadMsg, acceptType);
            }

            // For Android 5.0+
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                String[] items = getResources().getStringArray(R.array.select_image);
                mUploadMessage = filePathCallback;
                new MaterialDialog.Builder(MainActivity.this)
                        .setTitle("添加图片")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                {
                                    if (which == 0) {//拍照
                                        Toast.makeText(MainActivity.this, "开发中", Toast.LENGTH_SHORT).show();
                                    } else if (which == 1) {//选择照片
                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/*");//图片文件,系统自动调用图库和文件管理
                                        intent.putExtra("outputFormat",
                                                Bitmap.CompressFormat.JPEG.toString());
                                        startActivityForResult(Intent.createChooser(intent, "选择照片"), WEB_CHOOSE_FILE);
                                    }
                                }
                            }
                        })
//                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
//                            @Override
//                            public void onDismiss(DialogInterface dialog) {
//                                mUploadMessage = null;
//                                Log.i("dismissListener", "弹出框dismiss");
//                            }
//                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setWebResult();
                            }
                        })
                        .show();
                return true;
            }
        });
        wvMain.loadUrl("file:///android_asset/index.html");
    }

    private void setWebResult() {
        if (mUploadCallbackAboveL != null) {
            mUploadCallbackAboveL.onReceiveValue(uris);
        }
        if (mUploadMessage != null) {
            mUploadMessage.onReceiveValue(uris);
        }
        mUploadCallbackAboveL = null;
        mUploadMessage = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case WEB_CHOOSE_FILE:
                uris[0] = data.getData();
                break;
            case WEB_PHOTOGRAPH:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        setWebResult();
    }
}
