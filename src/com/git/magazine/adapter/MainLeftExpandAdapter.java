package com.git.magazine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.git.magazine.R;
import com.git.magazine.entity.Column;
import com.git.magazine.entity.Type;

public class MainLeftExpandAdapter extends BaseExpandableListAdapter {
	/***
	 * 数据源
	 */
	private ArrayList<Type> groupList;
	private ArrayList<List<Column>> childList;
	private LayoutInflater inflater;

	public MainLeftExpandAdapter(Context context, ArrayList<Type> groupList,
			ArrayList<List<Column>> childList) {
		this.groupList = groupList;
		this.childList = childList;
		inflater = LayoutInflater.from(context);
	}

	// 返回父列表个数
	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	// 返回子列表个数
	@Override
	public int getChildrenCount(int groupPosition) {
		return childList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {

		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {

		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TypeHolder TypeHolder = null;
		if (convertView == null) {
			TypeHolder = new TypeHolder();
			convertView = inflater.inflate(R.layout.group, null);
			TypeHolder.textView = (TextView) convertView
					.findViewById(R.id.group_name);
			TypeHolder.imageView = (TextView) convertView
					.findViewById(R.id.group_url);
			convertView.setTag(TypeHolder);
		} else {
			TypeHolder = (TypeHolder) convertView.getTag();
		}
		TypeHolder.textView.setText(((Type) groupList.get(groupPosition))
				.getName());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ColumnHolder ColumnHolder = null;
		if (convertView == null) {
			ColumnHolder = new ColumnHolder();
			convertView = inflater.inflate(R.layout.child, null);

			ColumnHolder.textName = (TextView) convertView
					.findViewById(R.id.child_name);
			ColumnHolder.textAge = (TextView) convertView
					.findViewById(R.id.child_url);
			convertView.setTag(ColumnHolder);
		} else {
			ColumnHolder = (ColumnHolder) convertView.getTag();
		}
		ColumnHolder.textName.setText(((Column) getChild(groupPosition,
				childPosition)).getName());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class TypeHolder {
		TextView textView;
		TextView imageView;
	}

	class ColumnHolder {
		TextView textName;
		TextView textAge;
	}
}
