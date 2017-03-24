package com.szcomtop.meal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Window,键盘操作类
 * @author wangchong
 */
public class WindowUtil {



	public static void observeSoftKeyboard(Activity activity, final OnSoftKeyboardChangeListener listener) {
		final View decorView = activity.getWindow().getDecorView();
		decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			int previousKeyboardHeight = -1;
			@Override
			public void onGlobalLayout() {
				Rect rect = new Rect();
				decorView.getWindowVisibleDisplayFrame(rect);
				int displayHeight = rect.bottom - rect.top;
				int height = decorView.getHeight();
				int keyboardHeight = height - displayHeight;
				if (previousKeyboardHeight != keyboardHeight) {
					boolean hide = (double) displayHeight / height > 0.8;
					listener.onSoftKeyBoardChange(keyboardHeight, !hide);
				}

				previousKeyboardHeight = height;

			}
		});
	}

	public interface OnSoftKeyboardChangeListener {
		void onSoftKeyBoardChange(int softKeybardHeight, boolean visible);
	}


	/**
	 * 打卡软键盘
	 *
	 *
	 */
	public static void showKeybord(EditText editText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 *
	 *
	 */
	public static void hideKeybord(EditText editText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}


	//获取是否存在NavigationBar
	public static boolean checkDeviceHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {

		}
		return hasNavigationBar;

	}

}
