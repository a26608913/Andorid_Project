package com.whq.mobilesafe.activity;

import com.whq.mobilesafe.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gv_home;
	private String[] mTitleStrs;
	private int[] mDarwableIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// 初始化界面
		initUI();
		// 初始化数据
		initData();
	}

	/**
	 * 初始化控件数据
	 */
	private void initData() {
		mTitleStrs = new String[] { "手机防盗", "通信卫士", "软件管理", "进程管理",
				"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };

		mDarwableIds = new int[] { R.drawable.home_safe,
				R.drawable.home_callmsgsafe, R.drawable.home_callmsgsafe,
				R.drawable.home_apps, R.drawable.home_taskmanager,
				R.drawable.home_netmanager, R.drawable.home_trojan,
				R.drawable.home_sysoptimize, R.drawable.home_tools,
				R.drawable.home_settings
		};
		//九宫格控件设置数据适配器
		gv_home.setAdapter(new MyAdapter());
		
		//注册九宫格的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			//position:点中列表条目索引
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				switch (position) {
				case 0:
					
					break;
				case 8:
					Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
					startActivity(intent);
					break;
				}
				
			}
		});
	}

	/**
	 * 加载控件
	 */
	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);
	}
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mTitleStrs.length;
		}

		@Override
		public Object getItem(int position) {
			return mTitleStrs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			iv_icon.setBackgroundResource(mDarwableIds[position]);
			tv_title.setText(mTitleStrs[position]);
			return view;
		}
		
	}

}
