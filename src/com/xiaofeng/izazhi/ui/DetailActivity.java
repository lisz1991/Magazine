package com.xiaofeng.izazhi.ui;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xiaofeng.izazhi.BaseActivity;
import com.xiaofeng.izazhi.R;
import com.xiaofeng.izazhi.Async.AsyncHttp;
import com.xiaofeng.izazhi.constance.Constance;
import com.xiaofeng.izazhi.entity.ZaZhi;
import com.xiaofeng.izazhi.listener.ImageZoomListener;
import com.xiaofeng.utils.T;

public class DetailActivity extends BaseActivity implements OnClickListener {
	public TextView detailName, detailCurrent, detailTime, detailUpdate,
			detailTotal, detailPrice;
	public ZaZhi zaZhi;
	public ImageView image;
	public Button read;
	public String pageTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_detail);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		image = (ImageView) findViewById(R.id.activity_ditail_image);
		image.setOnTouchListener(new ImageZoomListener());
		read = (Button) findViewById(R.id.activity_ditail_button);
		read.setOnClickListener(this);
		detailName = (TextView) findViewById(R.id.activity_ditail_book_name);
		detailCurrent = (TextView) findViewById(R.id.activity_ditail_book_current);
		detailTime = (TextView) findViewById(R.id.activity_ditail_book_time);
		detailUpdate = (TextView) findViewById(R.id.activity_ditail_book_update);
		detailTotal = (TextView) findViewById(R.id.activity_ditail_book_total);
		detailPrice = (TextView) findViewById(R.id.activity_ditail_book_price);
		getActionBar().setTitle("详情");
	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		zaZhi = (ZaZhi) intent.getSerializableExtra("ZaZhi");
		if (null == zaZhi || null== zaZhi.urlImage || null== zaZhi.urlDetail) {
			T.show(mContext, "解析数据失败!", Toast.LENGTH_LONG);
			this.finish();
		}
		ImageLoader.getInstance().displayImage(zaZhi.urlImage, image);
		String[] params = { Constance.SERVER + zaZhi.urlDetail,
				String.valueOf(AsyncHttp.PAGE_DETAIL) };
		AsyncHttp.getInstance(this, hand).execute(params);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_ditail_button:
			Intent intent = new Intent(DetailActivity.this, ReadActivity.class);
			intent.putExtra("ZaZhi", zaZhi);
			startActivity(intent);
			break;
		}
	}

	public void getInfo(Document doc) {
		Elements ths = doc.getElementsByClass("table");
		String name = ths.get(0).getElementsByTag("td").get(1).text();
		detailName.setText(name);
		getActionBar().setTitle(name);
		String current = ths.get(0).getElementsByTag("td").get(3).text();
		detailCurrent.setText(current);
		String time = ths.get(0).getElementsByTag("td").get(5).text();
		detailTime.setText(time);
		String update = ths.get(1).getElementsByTag("td").get(1).text();
		detailUpdate.setText(update);
		String total = ths.get(1).getElementsByTag("td").get(7).text();
		detailTotal.setText(total);
		String price = ths.get(1).getElementsByTag("td").get(5).text();
		detailPrice.setText(price);
//		pageTotal=
	}

	public Handler hand = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case AsyncHttp.PAGE_DETAIL:
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
