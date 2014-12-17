package com.git.magazine.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.git.magazine.R;
import com.git.magazine.entity.MagazineInfo;
import com.git.magazine.listener.ImageZoomListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MagazineDescPopup extends PopupWindow{

	private static final String TAG = "MagazineDescPopup";
	private TextView detailName, detailCurrent, detailTime, detailUpdate,
			detailTotal, detailPrice;
	private ImageView posterImage;
	private View contentView;

	public MagazineDescPopup(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		contentView = inflater.inflate(R.layout.activity_detail, null);
		initDescView();
		
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		// 设置MagazineDescPopup的View
		this.setContentView(contentView);
		// 设置MagazineDescPopup弹出窗体的宽
		this.setWidth(LayoutParams.WRAP_CONTENT);
		// 设置MagazineDescPopup弹出窗体的高
		this.setHeight(LayoutParams.FILL_PARENT);
		// 设置MagazineDescPopup弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 实例化一个ColorDrawable颜色为半透明
		int color = Color.argb(127, 255, 255, 255);  
		ColorDrawable dw = new ColorDrawable(color);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);

		this.setAnimationStyle(R.style.magazinedescPopupTheme);
		
	}

	private void initDescView() {
//		posterImage = (ImageView) contentView
//				.findViewById(R.id.activity_ditail_image);
//		posterImage.setOnTouchListener(new ImageZoomListener());
		detailName = (TextView) contentView
				.findViewById(R.id.activity_ditail_book_name);
		detailCurrent = (TextView) contentView
				.findViewById(R.id.activity_ditail_book_current);
		detailTime = (TextView) contentView
				.findViewById(R.id.activity_ditail_book_time);
		detailUpdate = (TextView) contentView
				.findViewById(R.id.activity_ditail_book_update);
		detailTotal = (TextView) contentView
				.findViewById(R.id.activity_ditail_book_total);
		detailPrice = (TextView) contentView
				.findViewById(R.id.activity_ditail_book_price);
	}
	
	/**
	 * 显示在指定控件的下方
	 * 
	 * @param parent
	 */
	public void showPopupWindow(View view, MagazineInfo magazineInfo) {
		if(!this.isShowing()){
			detailName.setText(magazineInfo.curName);
			detailCurrent.setText(magazineInfo.detailCurrent);
			detailTime.setText(magazineInfo.detailTime);
			detailUpdate.setText(magazineInfo.detailUpdate);
			detailTotal.setText(magazineInfo.detailTotal);
			detailPrice.setText(magazineInfo.detailPrice);
//			ImageLoader.getInstance().displayImage(magazineInfo.urlImage, posterImage);
			this.showAsDropDown(view);
		}else{
			this.dismiss();
		}
		
	}
}
