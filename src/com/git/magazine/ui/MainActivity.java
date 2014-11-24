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
	private ListView mListView;
	private SlidingMenu menu;
	private List<MagazineInfo> mZaZhisOne, mZaZhisTwo, mZaZhisThree, mZaZhisFour,
			mZaZhisFive, mZaZhisSix, mZaZhisSeven;
	private static int mCurrentType = 0;
	private static final int TYPE_NEWS = 1, TYPE_MONEY = 2, TYPE_GAME = 3,
			TYPE_TECH = 4, TYPE_POPLE = 5, TYPE_LIFE = 6, TYPE_SPORT = 7;
	private ExpandableList expandableListView;
	private StickyLayout stickyLayout;
	private MainLeftExpandAdapter adapter;

	private ArrayList<Type> groupList;
	private ArrayList<List<Column>> childList;

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
		mListView = (ListView) findViewById(R.id.main_listView);
		mListView.setOnItemClickListener(new ListClick());

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

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.item_1:
	// if (null != mZaZhisOne) {
	// mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisOne));
	// return;
	// }
	// click(TYPE_NEWS, Constance.NEWS);
	// break;
	// case R.id.item_2:
	// if (null != mZaZhisTwo) {
	// mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisTwo));
	// return;
	// }
	// click(TYPE_MONEY, Constance.MONEY);
	// break;
	// case R.id.item_3:
	// if (null != mZaZhisThree) {
	// mListView
	// .setAdapter(new MainTypeListAdapter(this, mZaZhisThree));
	// return;
	// }
	// click(TYPE_GAME, Constance.GAME);
	// break;
	// case R.id.item_4:
	// if (null != mZaZhisFour) {
	// mListView
	// .setAdapter(new MainTypeListAdapter(this, mZaZhisFour));
	// return;
	// }
	// click(TYPE_TECH, Constance.TECH);
	// break;
	// case R.id.item_5:
	// if (null != mZaZhisFive) {
	// mListView
	// .setAdapter(new MainTypeListAdapter(this, mZaZhisFive));
	// return;
	// }
	// click(TYPE_POPLE, Constance.POPLE);
	// break;
	// case R.id.item_6:
	// if (null != mZaZhisSix) {
	// mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisSix));
	// return;
	// }
	// click(TYPE_LIFE, Constance.LIFE);
	// break;
	// case R.id.item_7:
	// if (null != mZaZhisSeven) {
	// mListView
	// .setAdapter(new MainTypeListAdapter(this, mZaZhisSeven));
	// return;
	// }
	// click(TYPE_SPORT, Constance.SPORT);
	// break;
	// }
	// }
	//
	// public void click(int a, String url) {
	// if (a == mCurrentType) {
	// return;
	// }
	// mCurrentType = a;
	// String[] params = { url, String.valueOf(AsyncHttp.PAGE_TYPE) };
	// AsyncHttp.getInstance(this, mHandelr).execute(params);
	// }

	public class ItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(MainActivity.this, FlipViewReadActivity.class);
			intent.putExtra("ZaZhi", mZaZhis.get(position));
			startActivity(intent);
		}
	}

	public class ListClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(MainActivity.this, SubActivity.class);
			switch (mCurrentType) {
			case TYPE_NEWS:
				intent.putExtra("ZaZhi", mZaZhisOne.get(position));
				break;
			case TYPE_MONEY:
				intent.putExtra("ZaZhi", mZaZhisTwo.get(position));
				break;
			case TYPE_GAME:
				intent.putExtra("ZaZhi", mZaZhisThree.get(position));
				break;
			case TYPE_TECH:
				intent.putExtra("ZaZhi", mZaZhisFour.get(position));
				break;
			case TYPE_POPLE:
				intent.putExtra("ZaZhi", mZaZhisFive.get(position));
				break;
			case TYPE_LIFE:
				intent.putExtra("ZaZhi", mZaZhisSix.get(position));
				break;
			case TYPE_SPORT:
				intent.putExtra("ZaZhi", mZaZhisSeven.get(position));
				break;
			}
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
		}
		mGridView.setAdapter(new MainGridviewAdapter(this, mZaZhis));
	}

	public void getData(Document doc) {
		List<MagazineInfo> datas = new ArrayList<MagazineInfo>();
		Elements inlines = doc.getElementsByClass(getString(R.string.inline));
		for (int i = 0; i < inlines.size() - 1; i++) {
			Elements cols = inlines.get(i).getElementsByTag(
					getString(R.string.li));
			for (int j = 0; j < cols.size(); j++) {
				MagazineInfo zaZhi = new MagazineInfo();
				String url = cols.get(j)
						.getElementsByTag(getString(R.string.a))
						.attr(getString(R.string.href));
				String name = cols.get(j)
						.getElementsByTag(getString(R.string.a)).text();
				zaZhi.setDetailName(name);
				zaZhi.setUrlRead(url.replace(getString(R.string.Issue),
						getString(R.string.OnLine)));
				zaZhi.setUrlTotal(url);
				datas.add(zaZhi);
			}
		}
		switch (mCurrentType) {
		case TYPE_NEWS:
			mZaZhisOne = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisOne));
			break;
		case TYPE_MONEY:
			mZaZhisTwo = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisTwo));
			break;
		case TYPE_GAME:
			mZaZhisThree = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisThree));
			break;
		case TYPE_TECH:
			mZaZhisFour = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisFour));
			break;
		case TYPE_POPLE:
			mZaZhisFive = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisFive));
			break;
		case TYPE_LIFE:
			mZaZhisSix = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisSix));
			break;
		case TYPE_SPORT:
			mZaZhisSeven = datas;
			mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisSeven));
			break;

		}
		mListView.setVisibility(View.VISIBLE);
		mGridView.setVisibility(View.GONE);
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
			case AsyncHttp.PAGE_TYPE:
				getData((Document) msg.obj);
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
	public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
			long arg3) {
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
			int arg3, long arg4) {
		return false;
	}
}
