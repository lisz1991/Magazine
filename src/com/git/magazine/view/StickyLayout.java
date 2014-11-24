package com.git.magazine.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class StickyLayout extends LinearLayout {
	private static final String TAG = "StickyLayout";
	private static final boolean DEBUG = true;

	public interface OnGiveUpTouchEventListener {
		public boolean giveUpTouchEvent(MotionEvent event);
	}

	private View mContent;
	private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

	private int mStatus = STATUS_EXPANDED;
	public static final int STATUS_EXPANDED = 1;
	public static final int STATUS_COLLAPSED = 2;

	private int mTouchSlop;

	// 分别记录上次滑动的坐标
	private int mLastX = 0;
	private int mLastY = 0;

	// 分别记录上次滑动的坐标(onInterceptTouchEvent)
	private int mLastXIntercept = 0;
	private int mLastYIntercept = 0;

	// 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
	private static final int TAN = 2;

	private boolean mIsSticky = true;
	private boolean mInitDataSucceed = false;
	private boolean mDisallowInterceptTouchEventOnHeader = true;

	public StickyLayout(Context context) {
		super(context);
	}

	public StickyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public StickyLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if (hasWindowFocus && mContent == null) {
			initData();
		}
	}

	private void initData() {
		int contentId = getResources().getIdentifier("sticky_content", "id",
				getContext().getPackageName());
		if (contentId != 0) {
			mContent = findViewById(contentId);
			mTouchSlop = ViewConfiguration.get(getContext())
					.getScaledTouchSlop();
			if (DEBUG) {
				Log.d(TAG, "mTouchSlop = " + mTouchSlop);
			}
		}
	}

	public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
		mGiveUpTouchEventListener = l;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int intercepted = 0;
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mLastXIntercept = x;
			mLastYIntercept = y;
			mLastX = x;
			mLastY = y;
			intercepted = 0;
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastXIntercept;
			int deltaY = y - mLastYIntercept;
			if (mDisallowInterceptTouchEventOnHeader) {
				intercepted = 0;
			} else if (Math.abs(deltaY) <= Math.abs(deltaX)) {
				intercepted = 0;
			} else if (mStatus == STATUS_EXPANDED && deltaY <= -mTouchSlop) {
				intercepted = 1;
			} else if (mGiveUpTouchEventListener != null) {
				if (mGiveUpTouchEventListener.giveUpTouchEvent(event)
						&& deltaY >= mTouchSlop) {
					intercepted = 1;
				}
			}
			break;
		}
		case MotionEvent.ACTION_UP: {
			intercepted = 0;
			mLastXIntercept = mLastYIntercept = 0;
			break;
		}
		default:
			break;
		}

		if (DEBUG) {
			Log.d(TAG, "intercepted=" + intercepted);
		}
		return intercepted != 0 && mIsSticky;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mIsSticky) {
			return true;
		}
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastX;
			int deltaY = y - mLastY;
			break;
		}
		case MotionEvent.ACTION_UP: {
			// 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
			break;
		}
		default:
			break;
		}
		mLastX = x;
		mLastY = y;
		return true;
	}

	public void smoothSetHeaderHeight(final int from, final int to,
			long duration) {
		smoothSetHeaderHeight(from, to, duration, false);
	}

	public void smoothSetHeaderHeight(final int from, final int to,
			long duration, final boolean modifyOriginalHeaderHeight) {
		final int frameCount = (int) (duration / 1000f * 30) + 1;
		final float partation = (to - from) / (float) frameCount;
		new Thread("Thread#smoothSetHeaderHeight") {

			@Override
			public void run() {
				for (int i = 0; i < frameCount; i++) {
					final int height;
					if (i == frameCount - 1) {
						height = to;
					} else {
						height = (int) (from + partation * i);
					}
					post(new Runnable() {
						public void run() {
						}
					});
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};

		}.start();
	}

	public void setSticky(boolean isSticky) {
		mIsSticky = isSticky;
	}

	public void requestDisallowInterceptTouchEventOnHeader(
			boolean disallowIntercept) {
		mDisallowInterceptTouchEventOnHeader = disallowIntercept;
	}

}