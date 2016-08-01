package com.moo.test.test_android.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.xutils.x;

public class VertualKeyboardManager {
	private InputMethodManager inputMethodManager;

	public VertualKeyboardManager() {
		this.inputMethodManager = (InputMethodManager) x.app().getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	/**
	 * 反转虚拟键盘，显示状态调用隐藏 隐藏状态调用显示
	 */
	public void toggleKeyboard() {
		if (inputMethodManager != null && inputMethodManager.isActive()) {
			inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	/**
	 * 显示虚拟键盘
	 */
	public void showKeyboard(View view) {
		if (inputMethodManager != null && inputMethodManager.isActive()) {
			inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}
	}
	/**
	 * 隐藏虚拟键盘
	 */
	public void hideKeyboard(View view) {
		if (inputMethodManager != null && inputMethodManager.isActive()) {
			inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
		}
	}
	/**
	 * 隐藏虚拟键盘
	 */
	public void hideKeyboardFromWindow(View view) {
		if (inputMethodManager != null && inputMethodManager.isActive()) {
			inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
		}
	}
}
