package com.moo.test.test_android.utils;

import android.os.Build;
import android.os.Environment;


import com.moo.test.test_android.app.BaseApp;

import java.io.File;


/**
 * Created by moodstrikerdd on 2015/12/4.
 */
public class FileUtils {
    /**
     * 获取存储根目录
     */
    public static File getRootSaveFile() {
        File rootFile;
        if (Build.VERSION.SDK_INT >= 14) {
            rootFile = BaseApp.getBaseApp().getExternalFilesDir(null);
//        Log.d("path", rootFile.getAbsolutePath());
        } else {
            Boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            if (hasSDCard) {
                rootFile = new File(Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + BaseApp.getBaseApp().getPackageName() + File.separator + "files");
//                Log.d("path", rootFile.getAbsolutePath());
            } else {
                rootFile = new File(Environment.getDataDirectory() + File.separator + BaseApp.getBaseApp().getPackageName() + File.separator + "files");
//        Log.d("path", rootFile.getAbsolutePath());
            }
        }

        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return rootFile;
    }

    /**
     * 获取缓存根目录
     */
    public static File getRootCatchFile() {
        File rootFile;
        if (Build.VERSION.SDK_INT >= 14) {
            rootFile = BaseApp.getBaseApp().getExternalCacheDir();
//        Log.d("path", rootFile.getAbsolutePath());
        } else {
            Boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            if (hasSDCard) {
                rootFile = new File(Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + BaseApp.getBaseApp().getPackageName() + File.separator + "cache");
//        Log.d("path", rootFile.getAbsolutePath());
            } else {
                rootFile = new File(Environment.getDataDirectory() + File.separator + BaseApp.getBaseApp().getPackageName() + File.separator + "cache");
//        Log.d("path", rootFile.getAbsolutePath());
            }
        }

        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        return rootFile;
    }

}
