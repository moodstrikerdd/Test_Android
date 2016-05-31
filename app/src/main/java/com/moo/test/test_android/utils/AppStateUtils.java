package com.moo.test.test_android.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;

public class AppStateUtils {
	private static Context mContext;

	private AppStateUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static void init(Context context) {
		mContext = context;
	}

	/**
	 * 获取本应用的名字
	 */
	public static String getAppName() {
		return getAppName(null);
	}

	/**
	 * 获取对应包名下的应用名称
	 */
	public static String getAppName(String packageName) {
		try {
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager
					.getPackageInfo(packageName == null ? mContext.getPackageName() : packageName, 0);
			return packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取本应用的图标
	 */
	public static Drawable getAppIcon() {
		return getAppIcon(null);
	}

	/**
	 * 获取对应包名下的应用图标
	 */
	public static Drawable getAppIcon(String packageName) {
		try {
			PackageManager packageManager = mContext.getPackageManager();
			return packageManager.getApplicationIcon(packageName == null ? mContext.getPackageName() : packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取对应包下所有的activity name
	 */

	public static List<String> getAppActivitiesName(String packageName) {
		List<String> activitiesName = null;
		try {
			activitiesName = new ArrayList<String>();
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager
					.getPackageInfo(packageName == null ? mContext.getPackageName() : packageName, 0);
			ActivityInfo[] activityInfos = packageInfo.activities;
			for (ActivityInfo activityInfo : activityInfos) {
				activitiesName.add(activityInfo.name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return activitiesName;
	}

	/**
	 * 获取本应用的所有activity name
	 */
	public static List<String> getAppActivitiesName() {
		return getAppActivitiesName(null);
	}

	/**
	 * 获取对应包下所有的service name
	 */

	public static List<String> getAppServicesName(String packageName) {
		List<String> servicesName = null;
		try {
			servicesName = new ArrayList<String>();
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager
					.getPackageInfo(packageName == null ? mContext.getPackageName() : packageName, 0);
			ServiceInfo[] serviceInfos = packageInfo.services;
			for (ServiceInfo serviceInfo : serviceInfos) {
				servicesName.add(serviceInfo.name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return servicesName;
	}

	/**
	 * 获取本应用的所有service name
	 */
	public static List<String> getAppServicesName() {
		return getAppServicesName(null);
	}

	/**
	 * 获取对应包下所有的receiver name
	 */

	public static List<String> getAppReceiversName(String packageName) {
		List<String> reciversName = null;
		try {
			reciversName = new ArrayList<String>();
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager
					.getPackageInfo(packageName == null ? mContext.getPackageName() : packageName, 0);
			ActivityInfo[] receiversInfos = packageInfo.receivers;
			for (ActivityInfo recieversInfo : receiversInfos) {
				reciversName.add(recieversInfo.name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reciversName;
	}

	/**
	 * 获取本应用的所有service name
	 */
	public static List<String> getAppReceiversName() {
		return getAppReceiversName(null);
	}

	/**
	 * 获取版本相关信息
	 * 
	 * @return
	 */
	public static int getVersionCode() {
		try {
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String getVersionName() {
		try {
			PackageManager packageManager = mContext.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
