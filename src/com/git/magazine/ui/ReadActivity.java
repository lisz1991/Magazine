package com.git.magazine.ui;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.Async.AsyncHttp;
import com.git.magazine.adapter.ReadListViewAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.MagazineInfo;
import com.git.magazine.utils.L;
import com.git.magazine.utils.T;
import com.git.magazine.view.MagazineDescPopup;
import com.git.magezine.frame.BaseActivity;

public class ReadActivity extends BaseActivity {
	public ListView mListView;
	public String mUrl, mBaseUrl;
	public List<String> mImages;
	public ReadListViewAdapter mReadAdapter;
	public int mPage;// 当前最后页数
	public int mFirst = 6;// 每次加载数量
	public MagazineInfo magazineInfo;
	private static final int FORMAT_DESC_DONE = 10000;
	private static final String TAG = "ReadActivity";
	private Handler handler;
	private TextView title;
	private MagazineDescPopup magazionDescpop;
	private ImageButton magazineDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_read);
		super.onCreate(savedInstanceState);
		initHandler();
		getMagazineDesc();
	}

	@Override
	public void initView() {
		magazionDescpop = new MagazineDescPopup(this);
		mListView = (ListView) findViewById(R.id.activity_read_listView);
		View actionBarView = LayoutInflater.from(this).inflate(
				R.layout.actionbar_layout, null);
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		// actionBar.setDisplayShowCustomEnabled(true);
		title = (TextView) actionBarView.findViewById(R.id.title_name);
		title.setText(R.string.app_first);
		magazineDesc = (ImageButton) actionBarView
				.findViewById(R.id.magazineDesc);
		magazineDesc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				magazionDescpop.showPopupWindow(magazineDesc, magazineInfo);
			}
		});
		actionBarView.findViewById(R.id.back_button).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						ReadActivity.this.finish();
					}
				});
	}

	private void initHandler() {
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {

				case AsyncHttp.PAGE_DETAIL:
					if (null == msg.obj) {
						T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
						return;
					}
					formatMagazineDesc((Document) msg.obj);
					break;
				case FORMAT_DESC_DONE:
					getMagazineContent();
					break;
				case AsyncHttp.PAGE_READ:
					if (null == msg.obj) {
						T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
						return;
					}
					configurPageUrl((Document) msg.obj);
					break;
				}
			}
		};
	}

	private void formatMagazineDesc(Document magazineDesc) {
		Elements ths = magazineDesc.getElementsByClass("table");
		String name = ths.get(0).getElementsByTag("td").get(1).text();
		magazineInfo.setCurName(name);
		String current = ths.get(0).getElementsByTag("td").get(3).text();
		magazineInfo.setDetailCurrent(current);
		String time = ths.get(0).getElementsByTag("td").get(5).text();
		magazineInfo.setDetailTime(time);
		String update = ths.get(1).getElementsByTag("td").get(1).text();
		magazineInfo.setDetailUpdate(update);
		String total = ths.get(1).getElementsByTag("td").get(7).text();
		magazineInfo.setDetailTotal(total);
		String price = ths.get(1).getElementsByTag("td").get(5).text();
		magazineInfo.setDetailPrice(price);
		String pageTotalDesc = ths.get(0).getElementsByTag("td").get(7).text();
		String pageTotal = pageTotalDesc.substring(
				pageTotalDesc.indexOf("/") + 1, pageTotalDesc.indexOf("页"));
		L.v(TAG, "totalPage:", pageTotal.replaceAll("\\s*", ""));
		magazineInfo.setPageTotal(pageTotal.replaceAll("\\s*", ""));

		handler.sendEmptyMessage(FORMAT_DESC_DONE);
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		magazineInfo = (MagazineInfo) intent.getSerializableExtra("ZaZhi");

		if (null == magazineInfo || null == magazineInfo.urlRead) {
			T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
			this.finish();
		}

		mUrl = magazineInfo.urlRead;
		title.setText(magazineInfo.curName);
		mImages = new ArrayList<String>();
		mReadAdapter = new ReadListViewAdapter(ReadActivity.this, mImages);
		mListView.setAdapter(mReadAdapter);
		mListView.setOnScrollListener(new ScrollListener());

		// mPage = 0;
		// String[] params = { Constance.SERVER + mUrl,
		// String.valueOf(AsyncHttp.PAGE_READ) };
		// AsyncHttp.getInstance(this, hand).execute(params);
	}

	private void getMagazineDesc() {
		String[] params = { Constance.SERVER + magazineInfo.urlDetail,
				String.valueOf(AsyncHttp.PAGE_DETAIL) };
		AsyncHttp.getInstance(this, handler).execute(params);

	}

	private void getMagazineContent() {
		String[] params = { Constance.SERVER + mUrl,
				String.valueOf(AsyncHttp.PAGE_READ) };
		AsyncHttp.getInstance(this, handler).execute(params);
	}

	public void getInfo(Document doc) {
		Elements pages = doc.getElementsByClass("outer_page");
		mBaseUrl = pages.get(0).getElementsByTag("img").get(0).attr("src");
		if (mBaseUrl.endsWith("1.jpg")) {
			for (int i = 1; i < mFirst; i++) {
				mImages.add(mBaseUrl.replace("1.jpg", String.valueOf(i)
						+ ".jpg"));
			}
			mPage = mFirst;
			mReadAdapter.notifyDataSetChanged();
		}
	}

	private void configurPageUrl(Document doc) {
		Elements pages = doc.getElementsByClass("outer_page");
		mBaseUrl = pages.get(0).getElementsByTag("img").get(0).attr("src");
		if (mBaseUrl.endsWith("1.jpg")) {
			for (int i = 1; i < mFirst; i++) {
				mImages.add(mBaseUrl.replace("1.jpg", String.valueOf(i)
						+ ".jpg"));
			}
			mPage = mFirst;
			mReadAdapter.notifyDataSetChanged();
		}
	}

	public class ScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem + visibleItemCount + 3 > totalItemCount) {
				L.e("firstVisibleItem", firstVisibleItem + "      "
						+ visibleItemCount + "    " + totalItemCount);
				if (null != mBaseUrl && mBaseUrl.endsWith("1.jpg")) {
					for (int i = mPage; i < mPage + mFirst; i++) {
						if (mPage + mFirst <= 50) {
							mImages.add(mBaseUrl.replace("1.jpg",
									String.valueOf(i) + ".jpg"));
						}
					}
					mPage += mFirst;
					mReadAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	public Handler hand = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case AsyncHttp.PAGE_READ:
				if (null == msg.obj) {
					T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
					return;
				}
				getInfo((Document) msg.obj);
				break;
			}
		}
	};
}
