package com.xiaofeng.izazhi.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaofeng.izazhi.BaseApp;
import com.xiaofeng.izazhi.R;
import com.xiaofeng.izazhi.entity.ZaZhi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainGridviewAdapter extends BaseAdapter {
	public Context con;
	public List<ZaZhi> items;

	public MainGridviewAdapter(Context con, List<ZaZhi> items) {
		super();
		this.con = con;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
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
			convertView = LayoutInflater.from(con).inflate(
					R.layout.activity_main_gridview_item, null);
			holder.image = (ImageView) convertView
					.findViewById(R.id.main_gridview_item_imageView);
			holder.text = (TextView) convertView
					.findViewById(R.id.main_gridview_item_textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		convertView.setTag(R.id.main_listview_item_imageView,
//				items.get(position).urlDetail);
//		convertView.setTag(R.id.main_listview_item_textView,
//				items.get(position).urlTotal);
		holder.text.setText(items.get(position).curName);
		ImageLoader.getInstance().displayImage(items.get(position).urlImage,
				holder.image, BaseApp.displayImageOptions);
		return convertView;
	}

	class ViewHolder {
		public TextView text;
		public ImageView image;
	}
}
