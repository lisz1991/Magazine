package com.git.magazine.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 打开或关闭键盘
 */
public class KeyBoardUtils {
	/**
	 * 打卡软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void openKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 * @param mContext
	 *            上下文
	 */
	public static void closeKeybord(EditText mEditText, Context mContext) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}

	/**
	 * 打开键盘
	 */
	public static void openKeyboard(Context mContext,View view) {
		InputMethodManager imme = (InputMethodManager) mContext
				.getApplicationContext().getSystemService(
						Context.INPUT_METHOD_SERVICE);
		imme.toggleSoftInputFromWindow(view.getWindowToken(), 0,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 关闭键盘
	 */
	public static void closeKeyboard(Context mContext,View view) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getApplicationContext().getSystemService(
						Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

}
