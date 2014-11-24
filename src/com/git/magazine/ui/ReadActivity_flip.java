package com.git.magazine.ui;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.git.magazine.Async.AsyncHttp;
import com.git.magazine.adapter.ReadFlipviewAdapter;
import com.git.magazine.adapter.ReadListViewAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.ZaZhi;
import com.git.magazine.utils.T;
import com.git.magezine.frame.BaseActivity;

public class ReadActivity_flip extends BaseActivity {
	public ListView mListView;
	public String mUrl, mBaseUrl;
	public ZaZhi mZaZhi;
	// public List<String> mImages;
	public ReadListViewAdapter mReadAdapter;
	public int mPage;// 当前最后页数
	public int mFirst = 6;// 每次加载数量

	// flip view

	private FlipViewController flipView;
	private int totalPageCount;
	private ReadFlipviewAdapter flipViewAdapter;
	public ArrayList<String> pageUrlList;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		flipView = new FlipViewController(this, FlipViewController.HORIZONTAL);
		initHandler();
		super.onCreate(savedInstanceState);
		setContentView(flipView);
	}

	private void initHandler() {
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
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

	@Override
	public void initView() {

	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		mZaZhi = (ZaZhi) intent.getSerializableExtra("ZaZhi");
		totalPageCount = Integer.parseInt(mZaZhi.pageTotal);
		pageUrlList = new ArrayList<String>();

		if (null == mZaZhi || null == mZaZhi.urlRead) {
			T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
			this.finish();
		}
		mUrl = mZaZhi.urlRead;
		getActionBar().setTitle(mZaZhi.curName);
		flipViewAdapter = new ReadFlipviewAdapter(this, pageUrlList);
		flipView.setAdapter(flipViewAdapter);

		String[] params = { Constance.SERVER + mUrl,
				String.valueOf(AsyncHttp.PAGE_READ) };
		AsyncHttp.getInstance(this, handler).execute(params);

	}

	private void configurPageUrl(Document doc) {
		if(totalPageCount > 50){
			totalPageCount = 51;
		}
		Elements pages = doc.getElementsByClass("outer_page");
		mBaseUrl = pages.get(0).getElementsByTag("img").get(0).attr("src");
		if (mBaseUrl.endsWith("1.jpg")) {
			for (int i = 1; i < totalPageCount; i++) {
				pageUrlList.add(mBaseUrl.replace("1.jpg", String.valueOf(i)
						+ ".jpg"));
			}
			mPage = mFirst;
			flipViewAdapter.updatePageUrlList(pageUrlList);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		flipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		flipView.onPause();
	}
}
