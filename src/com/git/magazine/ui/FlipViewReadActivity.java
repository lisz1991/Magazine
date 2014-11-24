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
import com.git.magazine.entity.MagazineInfo;
import com.git.magazine.utils.L;
import com.git.magazine.utils.T;
import com.git.magezine.frame.BaseActivity;

public class FlipViewReadActivity extends BaseActivity {
	public ListView mListView;
	public String mUrl, mBaseUrl;
	// public List<String> mImages;
	public ReadListViewAdapter mReadAdapter;
	public int mPage;// 当前最后页数
	public int mFirst = 6;// 每次加载数量

	// flip view
	private static final String TAG = "FlipViewReadActivity";
	private static final int FORMAT_DESC_DONE = 10000;
	public MagazineInfo magazineInfo;
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
		getMagazineDesc();
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

	@Override
	public void initView() {

	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		magazineInfo = (MagazineInfo) intent.getSerializableExtra("ZaZhi");
		totalPageCount = Integer.parseInt(magazineInfo.pageTotal);
		pageUrlList = new ArrayList<String>();

		if (null == magazineInfo || null == magazineInfo.urlRead) {
			T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
			this.finish();
		}
		mUrl = magazineInfo.urlRead;
		getActionBar().setTitle(magazineInfo.curName);
		flipViewAdapter = new ReadFlipviewAdapter(this, pageUrlList);
		flipView.setAdapter(flipViewAdapter);
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
	
	private void formatMagazineDesc(Document magazineDesc){
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
		String pageTotal = pageTotalDesc.substring(pageTotalDesc.indexOf("/")+1, pageTotalDesc.indexOf("页"));
		L.v(TAG, "totalPage:", pageTotal.replaceAll("\\s*", ""));
		magazineInfo.setPageTotal(pageTotal.replaceAll("\\s*", ""));
	}

	private void configurPageUrl(Document doc) {
		if (totalPageCount > 50) {
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
