package com.xiaofeng.izazhi.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaofeng.izazhi.BaseApp;
import com.xiaofeng.izazhi.R;
import com.xiaofeng.izazhi.adapter.MainGridviewAdapter.ViewHolder;
import com.xiaofeng.izazhi.listener.ImageZoomListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadListViewAdapter extends BaseAdapter {
	private Context mContext;
	private List<String> mUrls;

	public ReadListViewAdapter(Context mContext, List<String> mUrls) {
		super();
		this.mContext = mContext;
		this.mUrls = mUrls;
	}

	@Override
	public int getCount() {
		return mUrls.size();
	}

	@Override
	public Object getItem(int position) {
		return mUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.activity_read_listview_item, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.activity_read_listview_imageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		holder.image.setOnTouchListener(new ImageZoomListener());
		ImageLoader.getInstance().displayImage(mUrls.get(position),
				holder.image,BaseApp.displayImageOptions);
		return convertView;
	}

	class ViewHolder {
		public ImageView image;
	}
}
