package com.git.magazine.ui;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.Async.AsyncHttp;
import com.git.magazine.adapter.ReadListViewAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.MagazineInfo;
import com.git.magazine.utils.L;
import com.git.magazine.utils.T;
import com.git.magezine.frame.BaseActivity;

public class ReadActivity extends BaseActivity {
	public ListView mListView;
	public String mUrl, mBaseUrl;
	public MagazineInfo mZaZhi;
	public List<String> mImages;
	public ReadListViewAdapter mReadAdapter;
	public int mPage;// 当前最后页数
	public int mFirst = 6;// 每次加载数量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_read);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		mListView = (ListView) findViewById(R.id.activity_read_listView);
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		mZaZhi = (MagazineInfo) intent.getSerializableExtra("ZaZhi");
		if (null == mZaZhi || null == mZaZhi.urlRead) {
			T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
			this.finish();
		}
		mUrl = mZaZhi.urlRead;
		getActionBar().setTitle(mZaZhi.curName);
		mImages = new ArrayList<String>();
		mReadAdapter = new ReadListViewAdapter(ReadActivity.this, mImages);
		mListView.setAdapter(mReadAdapter);
		mListView.setOnScrollListener(new ScrollListener());
		mPage = 0;
		String[] params = { Constance.SERVER + mUrl,
				String.valueOf(AsyncHttp.PAGE_READ) };
		AsyncHttp.getInstance(this, hand).execute(params);
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
