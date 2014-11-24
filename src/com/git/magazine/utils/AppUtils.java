package com.git.magazine.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.ui.MainActivity;

public class AppUtils {
	public static final String TAG = "AndroidUtils";
	public static Context mContext;
	public static AppUtils mAppUtils;

	public static AppUtils getInstance(Context con) {
		mContext = con;
		if (mAppUtils == null) {
			return new AppUtils();
		}
		return mAppUtils;
	}

	/**
	 * 是否有快捷图标
	 */
	public static boolean isShortcut() {
		Cursor cursor = null;
		String url = null;
		if (android.os.Build.VERSION.SDK_INT < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = mContext.getApplicationContext()
				.getContentResolver();
		try {
			cursor = resolver.query(
					Uri.parse(url),
					null,
					"title=?",
					new String[] { mContext.getApplicationContext().getString(
							R.string.app_name) }, null);
			if (cursor == null) {
				url = "content://com.android.launcher.settings/favorites?notify=true";
				cursor = resolver.query(Uri.parse(url), null, "title=?",
						new String[] { mContext.getApplicationContext()
								.getString(R.string.app_name) }, null);
			}
			if (cursor != null) {
				Log.v(TAG, "cursor Count  : " + cursor.getCount());
			}
			if (cursor != null && cursor.getCount() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return false;
	}

	/**
	 * 创建快捷图标
	 */
	public static void createCut() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.setClass(mContext.getApplicationContext(), MainActivity.class);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				mContext.getApplicationContext(), R.drawable.ic_launcher);
		Intent addShortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, mContext
				.getApplicationContext().getString(R.string.app_name));
		addShortcut.putExtra("duplicate", false);
		addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		mContext.getApplicationContext().sendBroadcast(addShortcut);
	}

	// Toast提示
	public static void showToast(Context context, String toastMsg) {
		showToast(context, toastMsg, Toast.LENGTH_SHORT);
	}

	public static void showToast(Context context, String toastMsg, int duration) {
		Toast.makeText(context, toastMsg, duration).show();
	}

	// 判断SIM卡是否可用
	public static boolean isSimAvailable() {
		Log.v(TAG, "isSimAvailable Start");
		String imsi = getIMSI();
		if (imsi != null && imsi.length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取当前版本名
	public static int getVersionNum(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = null;
		int versionNum = 0;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionNum = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionNum;
	}

	// 获取当前手机串号
	public static String getIMSI() {
		TelephonyManager tm = getTeleManager();
		String imsi = tm.getSubscriberId();
		return null != imsi ? imsi : "";
	}

	public static String getMAC() {
		String macSerial = "";
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return macSerial;
	}

	public static int getSDK() {
		int verion = android.os.Build.VERSION.SDK_INT;
		return verion;
	}

	public static String getPhoneNum(Context context) {
		Log.v("getPhoneNum", "Start");
		String pnum = "";
		if (context != null) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm != null && tm.getLine1Number() != null) {
				pnum = tm.getLine1Number();
				Log.v("getPhoneNum no.=", pnum);
			}
		}
		return pnum;
	}

	public static String getIMEI(Context context) {
		if (null == context) {
			return "";
		}
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (imei == null)
			imei = "";
		return imei;
	}

	public static int[] getWidthAndHeight() {
		DisplayMetrics displayMetrics = mContext.getApplicationContext()
				.getResources().getDisplayMetrics();
		int h = displayMetrics.heightPixels;
		int w = displayMetrics.widthPixels;
		return new int[] { w, h };
	}

	public static String getTMV() {
		String tmv = android.os.Build.VERSION.RELEASE;
		if (tmv.length() > 3) {
			tmv = android.os.Build.VERSION.RELEASE.substring(0,
					android.os.Build.VERSION.RELEASE.lastIndexOf("."));
		}
		if (android.os.Build.VERSION.RELEASE.lastIndexOf(".") != -1) {
			return tmv;
		}
		return "";
	}

	public static String getMD5(String str) {
		MessageDigest message = null;
		try {
			message = MessageDigest.getInstance("MD5");
			message.reset();
			message.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = message.digest();
		StringBuffer md5Str = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5Str.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5Str.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5Str.toString();
	}

	public static TelephonyManager getTeleManager() {
		return (TelephonyManager) mContext.getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
	}

	public static ConnectivityManager getConnectivityManager() {
		return (ConnectivityManager) mContext.getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public static ContentResolver getContentResolver() {
		return mContext.getApplicationContext().getContentResolver();
	}

	public static AssetManager getAssetManager() {
		return mContext.getApplicationContext().getAssets();
	}

	public static void closeInput(InputStream fis) {
		if (null != fis) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeOutput(OutputStream os) {
		if (null != os) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String encode(String seed, String cleartext) {
		try {
			byte[] result = encode(seed.getBytes(), cleartext.getBytes());
			byte[] data = Base64.encode(result, Base64.URL_SAFE
					| Base64.NO_WRAP);
			String str = new String(data);
			return URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			Log.v(TAG, e.getMessage());
		}
		return "";
	}

	public static byte[] encode(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}
}