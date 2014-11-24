package com.git.magazine.ui;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.Async.AsyncHttp;
import com.git.magazine.adapter.MainGridviewAdapter;
import com.git.magazine.adapter.MainTypeListAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.MagazineInfo;
import com.git.magazine.utils.T;
import com.git.magazine.view.SlidingMenu;
import com.git.magezine.frame.BaseActivity;

public class MainActivity extends BaseActivity implements OnClickListener {
	private List<MagazineInfo> mZaZhis;
	private GridView mGridView;
	private ListView mListView;
	private SlidingMenu menu;
	private List<MagazineInfo> mZaZhisOne, mZaZhisTwo, mZaZhisThree, mZaZhisFour,
			mZaZhisFive, mZaZhisSix, mZaZhisSeven;
	private static int mCurrentType = 0;
	private static final int TYPE_NEWS = 1, TYPE_MONEY = 2, TYPE_GAME = 3,
			TYPE_TECH = 4, TYPE_POPLE = 5, TYPE_LIFE = 6, TYPE_SPORT = 7;

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
		menu.findViewById(R.id.item_1).setOnClickListener(this);
		menu.findViewById(R.id.item_2).setOnClickListener(this);
		menu.findViewById(R.id.item_3).setOnClickListener(this);
		menu.findViewById(R.id.item_4).setOnClickListener(this);
		menu.findViewById(R.id.item_5).setOnClickListener(this);
		menu.findViewById(R.id.item_6).setOnClickListener(this);
		menu.findViewById(R.id.item_7).setOnClickListener(this);

		getActionBar().setTitle("推荐");
	}

	@Override
	public void initData() {
		String[] params = { Constance.SERVER,
				String.valueOf(AsyncHttp.PAGE_MAIN) };
		AsyncHttp.getInstance(this, mHandelr).execute(params);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.item_1:
			if (null != mZaZhisOne) {
				mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisOne));
				return;
			}
			click(TYPE_NEWS, Constance.NEWS);
			break;
		case R.id.item_2:
			if (null != mZaZhisTwo) {
				mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisTwo));
				return;
			}
			click(TYPE_MONEY, Constance.MONEY);
			break;
		case R.id.item_3:
			if (null != mZaZhisThree) {
				mListView
						.setAdapter(new MainTypeListAdapter(this, mZaZhisThree));
				return;
			}
			click(TYPE_GAME, Constance.GAME);
			break;
		case R.id.item_4:
			if (null != mZaZhisFour) {
				mListView
						.setAdapter(new MainTypeListAdapter(this, mZaZhisFour));
				return;
			}
			click(TYPE_TECH, Constance.TECH);
			break;
		case R.id.item_5:
			if (null != mZaZhisFive) {
				mListView
						.setAdapter(new MainTypeListAdapter(this, mZaZhisFive));
				return;
			}
			click(TYPE_POPLE, Constance.POPLE);
			break;
		case R.id.item_6:
			if (null != mZaZhisSix) {
				mListView.setAdapter(new MainTypeListAdapter(this, mZaZhisSix));
				return;
			}
			click(TYPE_LIFE, Constance.LIFE);
			break;
		case R.id.item_7:
			if (null != mZaZhisSeven) {
				mListView
						.setAdapter(new MainTypeListAdapter(this, mZaZhisSeven));
				return;
			}
			click(TYPE_SPORT, Constance.SPORT);
			break;
		}
	}

	public void click(int a, String url) {
		if (a == mCurrentType) {
			return;
		}
		mCurrentType = a;
		String[] params = { url, String.valueOf(AsyncHttp.PAGE_TYPE) };
		AsyncHttp.getInstance(this, mHandelr).execute(params);
	}

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
		Elements hovers = doc.getElementsByClass("hover");
		Elements descriptionas = doc.getElementsByClass("description");
		for (int i = 0; i < descriptionas.size(); i++) {
			MagazineInfo zaZhi = new MagazineInfo();
			String url = hovers.get(i).getElementsByTag("a").attr("href");
			String name = hovers.get(i).getElementsByTag("a").attr("title");
			String src = hovers.get(i).getElementsByTag("img").attr("src");
			String main = descriptionas.get(i).getElementsByTag("a")
					.attr("href");
			zaZhi.setCurName(name);
			zaZhi.setUrlDetail(url);
			zaZhi.setUrlRead(url.replace("Issue", "OnLine"));
			zaZhi.setUrlTotal(main);
			zaZhi.setUrlImage(src);
			mZaZhis.add(zaZhi);
		}
		mGridView.setAdapter(new MainGridviewAdapter(this, mZaZhis));
	}

	public void getData(Document doc) {
		List<MagazineInfo> datas = new ArrayList<MagazineInfo>();
		Elements inlines = doc.getElementsByClass("list-inline");
		for (int i = 0; i < inlines.size() - 1; i++) {
			Elements cols = inlines.get(i).getElementsByTag("li");
			for (int j = 0; j < cols.size(); j++) {
				MagazineInfo zaZhi = new MagazineInfo();
				String url = cols.get(j).getElementsByTag("a").attr("href");
				String name = cols.get(j).getElementsByTag("a").text();
				zaZhi.setDetailName(name);
				zaZhi.setUrlRead(url.replace("Issue", "OnLine"));
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
				T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
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
}
