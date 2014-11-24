package com.git.magazine.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.adapter.MainLeftExpandAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.Column;
import com.git.magazine.entity.Type;
import com.git.magazine.view.ExpandableList;
import com.git.magazine.view.ExpandableList.OnHeaderUpdateListener;
import com.git.magazine.view.StickyLayout;
import com.git.magazine.view.StickyLayout.OnGiveUpTouchEventListener;

public class ExpandActivity extends Activity implements
		ExpandableListView.OnChildClickListener,
		ExpandableListView.OnGroupClickListener, OnHeaderUpdateListener,
		OnGiveUpTouchEventListener {
	private ExpandableList expandableListView;
	private StickyLayout stickyLayout;
	private MainLeftExpandAdapter adapter;

	private ArrayList<Type> groupList;
	private ArrayList<List<Column>> childList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_left);
		expandableListView = (ExpandableList) findViewById(R.id.expandablelist);
		stickyLayout = (StickyLayout) findViewById(R.id.sticky_layout);
		initData();
	}

	/***
	 * InitData
	 */
	void initData() {
		groupList = new ArrayList<Type>();
		Type group = null;
		for (int i = 0; i < Constance.TYPES_NAMES.length; i++) {
			group = new Type();
			group.setName(Constance.TYPES_NAMES[i]);
			groupList.add(group);
		}

		childList = new ArrayList<List<Column>>();
		for (int i = 0; i < groupList.size(); i++) {
			ArrayList<Column> childTemp;
			if (i == 0) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_NEWS.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_NEWS[j]);
					people.setUrl(Constance.TYPES_URLS_NEWS[j]);
					childTemp.add(people);
				}
			} else if (i == 1) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_MONEY.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_MONEY[j]);
					people.setUrl(Constance.TYPES_URLS_MONEY[j]);
					childTemp.add(people);
				}
			} else if (i == 2) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_GAME.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_GAME[j]);
					people.setUrl(Constance.TYPES_NAMES_GAME[j]);
					childTemp.add(people);
				}
			} else if (i == 3) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_TECH.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_TECH[j]);
					people.setUrl(Constance.TYPES_NAMES_TECH[j]);
					childTemp.add(people);
				}
			} else if (i == 4) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_POPLE.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_POPLE[j]);
					people.setUrl(Constance.TYPES_NAMES_POPLE[j]);
					childTemp.add(people);
				}
			} else if (i == 5) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_LIFE.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_LIFE[j]);
					people.setUrl(Constance.TYPES_NAMES_LIFE[j]);
					childTemp.add(people);
				}
			} else {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_SPORT.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_SPORT[j]);
					people.setUrl(Constance.TYPES_NAMES_SPORT[j]);
					childTemp.add(people);
				}
			}
			childList.add(childTemp);
		}
		adapter = new MainLeftExpandAdapter(this, groupList, childList);
		expandableListView.setAdapter(adapter);

		expandableListView.setOnHeaderUpdateListener(this);
		expandableListView.setOnChildClickListener(this);
		expandableListView.setOnGroupClickListener(this);
		stickyLayout.setOnGiveUpTouchEventListener(this);
	}

	@Override
	public boolean onGroupClick(final ExpandableListView parent, final View v,
			int groupPosition, final long id) {
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		Toast.makeText(ExpandActivity.this,
				childList.get(groupPosition).get(childPosition).getName(), 1)
				.show();

		return false;
	}

	@Override
	public View getPinnedHeader() {
		View headerView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.group, null);
		headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		return headerView;
	}

	@Override
	public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {
		Type firstVisibleGroup = (Type) adapter.getGroup(firstVisibleGroupPos);
		TextView textView = (TextView) headerView.findViewById(R.id.group_name);
		textView.setText(((Type) firstVisibleGroup).getName());
	}

	@Override
	public boolean giveUpTouchEvent(MotionEvent event) {
		if (expandableListView.getFirstVisiblePosition() == 0) {
			View view = expandableListView.getChildAt(0);
			if (view != null && view.getTop() >= 0) {
				return true;
			}
		}
		return false;
	}
}
