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
			// ������֤�ɹ�----->������ĸ������������ã�ͣ����������ɹ����б����
			setContentView(R.layout.activity_setup_over);
			
			initUI();
		} else {
			// ������֤�ɹ�---->�ĸ���������û�����ã���ת����������1
			Intent intent = new Intent(this, Setup1Activity.class);
			startActivity(intent);
			// ������һ���µĽ���󣬹رչ��ܽ���
			finish();
		}
	}		

	/**
	 * ������ɽ���
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
