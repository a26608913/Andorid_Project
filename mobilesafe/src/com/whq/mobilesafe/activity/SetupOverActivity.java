package com.whq.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;

public class SetupOverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
		if (setup_over) {
			// 密码验证成功----->并完成四个导航界面设置，停留在设置完成功能列表界面
			setContentView(R.layout.activity_setup_over);
			
			initUI();
		} else {
			// 密码验证成功---->四个导航界面没有设置，跳转到导航界面1
			Intent intent = new Intent(this, Setup1Activity.class);
			startActivity(intent);
			// 开启了一个新的界面后，关闭功能界面
			finish();
		}
	}		

	/**
	 * 设置完成界面
	 */
	private void initUI() {
		TextView tv_phone_protected = (TextView) findViewById(R.id.tv_phone);
		TextView tv_btn_reset = (TextView) findViewById(R.id.tv_btn_reset);
		String phone_num = SpUtil.getString(this, ConstantValue.PHONE_NUM, null);
		tv_phone_protected.setText(phone_num);
		
		tv_btn_reset.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
				startActivity(intent);
				finish();
			}
		});

		
	}
}
