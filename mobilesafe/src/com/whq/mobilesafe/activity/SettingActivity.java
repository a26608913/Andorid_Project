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
		// ����SettingItemView��Ŀ��checkboxѡ��״̬
		initUpdate();
	}

	/**
	 * ����SettingItemView��Ŀ��checkboxѡ��״̬
	 */
	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
		// ��ȡ��ȡ�õĿ���״̬,������ʾ
		boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
		// ѡ���ϴ��Ƿ�ѡ�еĽ��ȥ����״̬
		Log.i(tag, "----------------open_update-------------"+open_update);
		siv_update.setCheck(open_update);

		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ��ȡ֮ǰ��״̬
				boolean ischeck = siv_update.isCheck();
				// ��ԭ��״̬ȡ��
				siv_update.setCheck(!ischeck);
				// ��ȡ����ֵ�洢��sp��
				SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !ischeck);
			}
		});
	}

}
