package com.moo.test.test_android.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;

public class LogcatFileManager {
	// 是否开启打印
	private boolean debug;
	// 单例
	private static LogcatFileManager INSTANCE = null;
	// LogCat文件保存位置
	private static String PATH_LOGCAT;
	// LogCat文件打印类
	private LogDumper mLogDumper = null;
	private int mPId;
	// 文件后缀日期格式
	private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
	// logcat文件中日期格式
	private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	// 默认目录
	private static String defaultPath = null;

	public void debug(boolean isDebug) {
		debug = isDebug;
	}

	public static LogcatFileManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LogcatFileManager();
		}
		return INSTANCE;
	}

	private LogcatFileManager() {
		mPId = android.os.Process.myPid();
	}

	public void startLogcatManager() {
		if (debug) {
			setFolderPath();
			if (mLogDumper == null) {
				mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
			}
			mLogDumper.start();
		}
	}

	public void stopLogcatManager() {
		if (debug) {
			if (mLogDumper != null) {
				mLogDumper.stopLogs();
				mLogDumper = null;
			}
		}
	}

	private void setFolderPath() {
		if (defaultPath == null)
			defaultPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File folder = new File(defaultPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("The logcat folder path is not a directory: " + defaultPath);
		}

		PATH_LOGCAT = defaultPath.endsWith("/") ? defaultPath : defaultPath + "/";
	}

	private class LogDumper extends Thread {
		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			try {
				out = new FileOutputStream(new File(dir, "logcat-" + simpleDateFormat1.format(new Date()) + ".log"),
						true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			/**
			 * * * log level：*:v , *:d , *:w , *:e , *:f , *:s * * Show the
			 * current mPID process level of E and W log. * *
			 */
			// cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			cmds = "logcat *:E *:D | grep \"(" + mPID + ")\"";
		}

		public void stopLogs() {
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (!(line.startsWith("E") || line.startsWith("D"))) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((simpleDateFormat2.format(new Date()) + "  " + line + "\n").getBytes());
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}
		}

	}
}