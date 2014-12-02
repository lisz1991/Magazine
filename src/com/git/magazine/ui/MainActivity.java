package com.git.magazine.ui;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.Async.AsyncHttp;
import com.git.magazine.adapter.MainGridviewAdapter;
import com.git.magazine.adapter.MainLeftExpandAdapter;
import com.git.magazine.adapter.MainTypeListAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.Column;
import com.git.magazine.entity.MagazineInfo;
import com.git.magazine.entity.Type;
import com.git.magazine.utils.L;
import com.git.magazine.utils.T;
import com.git.magazine.view.ExpandableList;
import com.git.magazine.view.ExpandableList.OnHeaderUpdateListener;
import com.git.magazine.view.SlidingMenu;
import com.git.magazine.view.StickyLayout;
import com.git.magazine.view.StickyLayout.OnGiveUpTouchEventListener;
import com.git.magezine.frame.BaseActivity;

public class MainActivity extends BaseActivity implements
		ExpandableListView.OnChildClickListener,
		ExpandableListView.OnGroupClickListener, OnHeaderUpdateListener,
		OnGiveUpTouchEventListener {
	private List<MagazineInfo> mZaZhis;
	private GridView mGridView;
	private SlidingMenu menu;
	private ExpandableList expandableListView;
	private StickyLayout stickyLayout;
	private MainLeftExpandAdapter adapter;
	private MainGridviewAdapter gridAdapter;

	private ArrayList<Type> groupList;
	private ArrayList<List<Column>> childList;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		mGridView = (GridView) findViewById(R.id.main_gridView);
		mGridView.setOnItemClickListener(new ItemClick());
		mGridView.setOnItemLongClickListener(new ItemLongClick());

		mZaZhis = new ArrayList<MagazineInfo>();
		gridAdapter = new MainGridviewAdapter(this, mZaZhis);
		mGridView.setAdapter(gridAdapter);

		menu = (SlidingMenu) findViewById(R.id.id_menu);
		expandableListView = (ExpandableList) menu
				.findViewById(R.id.expandablelist);
		stickyLayout = (StickyLayout) menu.findViewById(R.id.sticky_layout);

		getActionBar().setTitle(getString(R.string.app_first));
	}

	@Override
	public void initData() {
		String[] params = { Constance.SERVER,
				String.valueOf(AsyncHttp.PAGE_MAIN) };
		AsyncHttp.getInstance(this, mHandelr).execute(params);

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
					people.setUrl(Constance.TYPES_URLS_GAME[j]);
					childTemp.add(people);
				}
			} else if (i == 3) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_TECH.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_TECH[j]);
					people.setUrl(Constance.TYPES_URLS_TECH[j]);
					childTemp.add(people);
				}
			} else if (i == 4) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_POPLE.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_POPLE[j]);
					people.setUrl(Constance.TYPES_URLS_POPLE[j]);
					childTemp.add(people);
				}
			} else if (i == 5) {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_LIFE.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_LIFE[j]);
					people.setUrl(Constance.TYPES_URLS_LIFE[j]);
					childTemp.add(people);
				}
			} else {
				childTemp = new ArrayList<Column>();
				for (int j = 0; j < Constance.TYPES_NAMES_SPORT.length; j++) {
					Column people = new Column();
					people.setName(Constance.TYPES_NAMES_SPORT[j]);
					people.setUrl(Constance.TYPES_URLS_SPORT[j]);
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


	public class ItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(MainActivity.this,
					DetailActivity.class);
			intent.putExtra("ZaZhi", mZaZhis.get(position));
			startActivity(intent);
		}
	}


	public class ItemLongClick implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			Intent intent = new Intent(MainActivity.this, SubActivity.class);
			intent.putExtra("ZaZhi", mZaZhis.get(position));
			startActivity(intent);
			return true;
		}
	}

	public void getInfo(Document doc) {
		mZaZhis = new ArrayList<MagazineInfo>();
		Elements hovers = doc.getElementsByClass(getString(R.string.hover));
		Elements descriptionas = doc
				.getElementsByClass(getString(R.string.description));
		for (int i = 0; i < descriptionas.size(); i++) {
			MagazineInfo zaZhi = new MagazineInfo();
			String url = hovers.get(i).getElementsByTag(getString(R.string.a))
					.attr(getString(R.string.href));
			String name = hovers.get(i).getElementsByTag(getString(R.string.a))
					.attr(getString(R.string.title));
			String src = hovers.get(i)
					.getElementsByTag(getString(R.string.img))
					.attr(getString(R.string.src));
			String main = descriptionas.get(i)
					.getElementsByTag(getString(R.string.a))
					.attr(getString(R.string.href));
			zaZhi.setCurName(name);
			zaZhi.setUrlDetail(url);
			zaZhi.setUrlRead(url.replace(getString(R.string.Issue),
					getString(R.string.OnLine)));
			zaZhi.setUrlTotal(main);
			zaZhi.setUrlImage(src);
			mZaZhis.add(zaZhi);
			L.v(TAG, "zazhi:" + i, zaZhi.toString());
		}
		gridAdapter.updateList(mZaZhis);
	}


	private Handler mHandelr = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (null == msg.obj) {
				T.show(mContext, R.string.getDataFail, Toast.LENGTH_LONG);
				return;
			}
			switch (msg.what) {
			case AsyncHttp.PAGE_MAIN:
				getInfo((Document) msg.obj);
				break;
			}
		}
	};

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

	@Override
	public View getPinnedHeader() {
		View headerView = (ViewGroup) getLayoutInflater().inflate(
				R.layout.activity_main_left_group, null);
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
	public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
			int arg3, long arg4) {
		TextView urlView = (TextView) arg1.findViewById(R.id.child_url);
		TextView nameView = (TextView) arg1.findViewById(R.id.child_name);
		String url = (String) urlView.getText();
		L.v(TAG, "httpUrl:", url);
		String name = (String) nameView.getText();
		String[] params = { Constance.SERVER + url,
				String.valueOf(AsyncHttp.PAGE_MAIN) };
		AsyncHttp.getInstance(this, mHandelr).execute(params);
		getActionBar().setTitle(name);
		return false;
	}
}
