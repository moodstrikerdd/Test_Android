/**
 * $RCSfile: CaptureActivity.java
 * $Revision: 1.0
 * $Date: 2014-11-30
 * <p/>
 * Copyright (C) 2014 SandBox, All rights reserved.
 * <p/>
 * This software is the proprietary information of SandBox,
 * Use is subject to license terms.
 */
package com.tiantianbook.okhttpdemo.zxing;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.tiantianbook.okhttpdemo.R;
import com.tiantianbook.okhttpdemo.zxing.camera.CameraManager;
import com.tiantianbook.okhttpdemo.zxing.decoding.CaptureActivityHandler;
import com.tiantianbook.okhttpdemo.zxing.decoding.InactivityTimer;
import com.tiantianbook.okhttpdemo.zxing.view.ViewfinderView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class CaptureActivity extends Activity implements Callback {

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    // private TextView txtResult;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private Handler mHandler;
    /**
     * scanBitmap 本地图片生成的位图
     */
    private Bitmap scanBitmap;

    private static final int DECODE_SUCCESS = 200;
    private static final int LIGHT_ON = 100;
    private static final int LIGHT_OFF = 99;
    private static final int ERROR = 50;
    /**
     * 图书馆id
     */
    public static CaptureActivity cap;
    private int currentType;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_zxing);
//        from_location = getIntent().getIntExtra(SCANNER, 0);
        currentType = CameraManager.TWO_DEMENTION_CODE;
//                currentType = CameraManager.SHAPE_CODE;
        // 屏幕一直亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 初始化 CameraManager
        CameraManager.init(getApplication(), currentType);
        initView();
        // 根据前置页面选择相应布局
        cap = this;
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                if (what == DECODE_SUCCESS) {
                    Toast.makeText(CaptureActivity.this, "msg"+msg.obj, Toast.LENGTH_SHORT).show();
                } else if (what == LIGHT_ON) {
                } else if (what == LIGHT_OFF) {
                } else if (what == ERROR) {
                    Toast.makeText(CaptureActivity.this, "图片无法识别", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    /**
     * 初始化需要的控件
     */
    private void initView() {
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
    }

    public void setQRImage(final String path) {
        Log.i("TAG", "path------->" + path);
        new Thread() {
            @Override
            public void run() {
                Log.i("TAG", "path------->" + path);
                String strResult = decodeQRImage(path);
                if (strResult != null) {
                    Message msg = new Message();
                    msg.what = DECODE_SUCCESS;
                    msg.obj = strResult;
                    mHandler.sendMessage(msg);
                } else {
                    mHandler.sendEmptyMessage(ERROR);
                }
            }
        }.start();
    }

    /**
     * 缩放图片
     *
     * @param bitmap 原图
     * @param width  新宽度
     * @param height 新高度
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        // 计算缩放比例
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        return newBitmap;
    }

    /**
     * 对QRcode图片进行解码
     *
     * @return 解码后的内容
     */
    public String decodeQRImage(String path) {
        String value = null;
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小

        int sampleSize = (int) (options.outHeight / (float) 200);

        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);

        // 初步解析图片，用于解析图的大小及相素等
        try {
            RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);

            // 将位图转换成一个BinaryBitmap（二进制组成的位图），HybridBinarizer二值化图片
            BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));

            // 数据解析类
            QRCodeReader reader2 = new QRCodeReader();
            Result result;
            //
            // //解析数据
            result = reader2.decode(bitmap1, hints);
            value = result.getText();
            return value;
        } catch (NotFoundException e) {
            e.printStackTrace();
            // } catch (ChecksumException e) {
            // e.printStackTrace();
        } catch (com.google.zxing.NotFoundException e) {
            // TODO 注意消除资源(关闭I/O等)
            e.printStackTrace();
            // } catch (com.google.zxing.FormatException e) {
            // // TODO 注意消除资源(关闭I/O等)
            // e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (com.google.zxing.FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用字符串生成二维码
     *
     * @param str
     * @return
     * @throws WriterException
     * @author zhouzhe@lenovo-cw.com
     */
    public Bitmap Create2DCode(String str) throws WriterException {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }

            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    /**
     * 返回解码的结果
     *
     * @param obj     解码的内容
     * @param barcode 扫描照片
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        Toast.makeText(CaptureActivity.this, obj.getText(), Toast.LENGTH_SHORT).show();
        inactivityTimer.onActivity();
        Toast.makeText(CaptureActivity.this, obj.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转到网页
     *
     * @param result 解码结果
     */
    private void goPageURL(String result) {
        Toast toast = Toast.makeText(this, "已扫描，正在处理...", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        // 判断是否是url链接
        if (result.contains("http://")) {// 是完整的url,就直接跳转
            Uri uri = Uri.parse(result.trim());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else {// 不是url就跳转百度搜索
            Uri uri = Uri.parse("http://www.baidu.com/baidu?wd=" + result.trim());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    public void turnBack(View v) {
        this.finish();
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     */
    public void saveImage(Bitmap bitmap) {
        // created folder
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/my2dcode");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(folder.getAbsolutePath() + "/mycode.jpg");
            // Bitmap.CompressFormat.PNG
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
