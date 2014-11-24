package com.git.magezine.frame;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.git.magazine.R;
import com.git.magazine.utils.AppUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * ****************
 * 
 * @Use:
 * @Date:2014-4-15
 * @Time:下午5:11:23
 */
public class BaseApp extends Application {
	public static Context context;
	public static DisplayImageOptions displayImageOptions;

	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		AppUtils.getInstance(this);
		if (!AppUtils.isShortcut()) {
			AppUtils.createCut();
		}
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).build();
		ImageLoader.getInstance().init(config);
		displayImageOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher)
				.imageScaleType(ImageScaleType.EXACTLY)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.cacheInMemory(true).cacheOnDisc(true)
				.displayer(new FadeInBitmapDisplayer(100))
				.imageScaleType(ImageScaleType.EXACTLY).build();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/**
	 * 重启应用
	 * */
	public static void doRestart(Context context) {
		try {
			if (context != null) {
				PackageManager pm = context.getPackageManager();
				if (pm != null) {
					Intent mStartActivity = pm
							.getLaunchIntentForPackage(context.getPackageName());
					if (mStartActivity != null) {
						mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						int mPendingIntentId = 223344;
						PendingIntent mPendingIntent = PendingIntent
								.getActivity(context, mPendingIntentId,
										mStartActivity,
										PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager mgr = (AlarmManager) context
								.getSystemService(Context.ALARM_SERVICE);
						mgr.set(AlarmManager.RTC,
								System.currentTimeMillis() + 100,
								mPendingIntent);
						System.exit(0);
					} else {
						Log.e("重启",
								"Was not able to restart application, mStartActivity null");
					}
				} else {
					Log.e("重启", "Was not able to restart application, PM null");
				}
			} else {
				Log.e("重启", "Was not able to restart application, Context null");
			}
		} catch (Exception ex) {
			Log.e("重启", "Was not able to restart application");
		}
	}
}
