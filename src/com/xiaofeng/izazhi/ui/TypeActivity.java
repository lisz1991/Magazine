package com.xiaofeng.izazhi.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.xiaofeng.izazhi.BaseActivity;
import com.xiaofeng.izazhi.R;
import com.xiaofeng.izazhi.view.SlidingMenu;

public class TypeActivity extends BaseActivity implements OnClickListener {
	public ListView mListView;
	public String mUrl;
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_type);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initView() {
		mListView = (ListView) findViewById(R.id.activity_type_listView);
		menu = (SlidingMenu) findViewById(R.id.id_menu);
		menu.findViewById(R.id.item_1).setOnClickListener(this);
		menu.findViewById(R.id.item_2).setOnClickListener(this);
		menu.findViewById(R.id.item_3).setOnClickListener(this);
		menu.findViewById(R.id.item_4).setOnClickListener(this);
		menu.findViewById(R.id.item_5).setOnClickListener(this);
		menu.findViewById(R.id.item_6).setOnClickListener(this);
		menu.findViewById(R.id.item_7).setOnClickListener(this);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
