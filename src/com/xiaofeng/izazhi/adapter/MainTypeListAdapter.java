package com.xiaofeng.izazhi.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaofeng.izazhi.R;
import com.xiaofeng.izazhi.entity.ZaZhi;

public class MainTypeListAdapter extends BaseAdapter {
	public Context con;
	public List<ZaZhi> items;

	public MainTypeListAdapter(Context con, List<ZaZhi> items) {
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
					R.layout.activity_main_listview_item, null);
			holder.text = (TextView) convertView
					.findViewById(R.id.main_listview_item_textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(items.get(position).detailName);
		return convertView;
	}

	class ViewHolder {
		public TextView text;
	}
}