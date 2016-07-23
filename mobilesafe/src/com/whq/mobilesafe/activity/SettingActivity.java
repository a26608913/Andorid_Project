package com.whq.mobilesafe.activity;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;
import com.whq.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	private static final String tag = "SettingActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		// 更新SettingItemView条目和checkbox选中状态
		initUpdate();
	}

	/**
	 * 更新SettingItemView条目和checkbox选中状态
	 */
	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
		// 获取已取得的开关状态,用作显示
		boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
		// 选择上次是否选中的结果去设置状态
		Log.i(tag, "----------------open_update-------------"+open_update);
		siv_update.setCheck(open_update);

		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获取之前的状态
				boolean ischeck = siv_update.isCheck();
				// 将原有状态取反
				siv_update.setCheck(!ischeck);
				// 将取反的值存储到sp中
				SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !ischeck);
			}
		});
	}

}
