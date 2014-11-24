package com.git.magazine.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.git.magazine.R;
import com.git.magezine.frame.BaseApp;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ReadFlipviewAdapter extends BaseAdapter {

	private ArrayList<String> pageUrlList;
	private LayoutInflater inflater;

	public ReadFlipviewAdapter(Context context, ArrayList<String> pageUrlList) {
		this.pageUrlList = pageUrlList;
		inflater = LayoutInflater.from(context);
	}

	public void updatePageUrlList(ArrayList<String> pageUrlList){
		this.pageUrlList = pageUrlList;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return pageUrlList.size();
	}

	@Override
	public Object getItem(int position) {
		return pageUrlList.get(position);
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
			convertView = inflater.inflate(R.layout.activity_readflip, null);
			holder.currentPage = (TextView) convertView
					.findViewById(R.id.currentPage);
			holder.totalPage = (TextView) convertView
					.findViewById(R.id.totalPage);
			holder.pageView = (ImageView) convertView
					.findViewById(R.id.pageView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.currentPage.setText(String.valueOf(position + 1));
		holder.totalPage.setText(String.valueOf(pageUrlList.size()));
		ImageLoader.getInstance().displayImage(pageUrlList.get(position),
				holder.pageView, BaseApp.displayImageOptions);
		return convertView;
	}

	class ViewHolder {
		TextView currentPage, totalPage;
		ImageView pageView;
	}

}
