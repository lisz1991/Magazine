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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.git.magazine.R;
import com.git.magazine.Async.AsyncHttp;
import com.git.magazine.adapter.MainGridviewAdapter;
import com.git.magazine.constance.Constance;
import com.git.magazine.entity.ZaZhi;
import com.git.magazine.utils.T;
import com.git.magezine.frame.BaseActivity;

public class SubActivity extends BaseActivity {
	private List<ZaZhi> mZaZhis;
	private GridView mGridView;
	private ZaZhi zaZhi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_sub);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		mGridView = (GridView) findViewById(R.id.activity_type_gridView);
		mGridView.setOnItemClickListener(new ItemClick());
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		zaZhi = (ZaZhi) intent.getSerializableExtra("ZaZhi");
		if (null== zaZhi || null == zaZhi.urlTotal) {
			T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
			this.finish();
		}
		String[] params = { Constance.SERVER + zaZhi.urlTotal,
				String.valueOf(AsyncHttp.PAGE_SUB) };
		AsyncHttp.getInstance(this, hand).execute(params);
	}

	public class ItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(SubActivity.this, DetailActivity.class);
			intent.putExtra("ZaZhi", mZaZhis.get(position));
			startActivity(intent);
		}
	}

	public void getInfo(Document doc) {
		Elements title = doc.getElementsByClass("page-title");
		getActionBar().setTitle(title.get(0).text());
		mZaZhis = new ArrayList<ZaZhi>();
		Elements hovers = doc.getElementsByClass("hover");
		Elements descriptionas = doc.getElementsByClass("description");
		for (int i = 0; i < descriptionas.size(); i++) {
			ZaZhi zaZhi = new ZaZhi();
			String url = hovers.get(i+1).getElementsByTag("a").attr("href");
			String src = hovers.get(i+1).getElementsByTag("img").attr("src");
			String name = descriptionas.get(i).getElementsByTag("h6").text();
			zaZhi.setCurName(name);
			zaZhi.setUrlDetail(url);
			zaZhi.setUrlRead(url.replace("Issue", "OnLine"));
			zaZhi.setUrlImage(src);
			mZaZhis.add(zaZhi);
		}
		mGridView.setAdapter(new MainGridviewAdapter(this, mZaZhis));
	}

	public Handler hand = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case AsyncHttp.PAGE_SUB:
				if (null == msg.obj) {
					T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
					Toast.makeText(mContext, "解析数据失败!!!!!", Toast.LENGTH_LONG)
							.show();
					return;
				}
				getInfo((Document) msg.obj);
				break;
			}
		}
	};
}
