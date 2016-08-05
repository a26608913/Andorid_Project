package com.whq.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;
import com.whq.mobilesafe.utils.ToastUtil;

public class Setup4Activity extends BaseSetupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_four);
		initUI();
	}

	private void initUI() {
		final CheckBox cb_box = (CheckBox) findViewById(R.id.cb_box);
		// 1.是否选中状态，回显
		boolean open_security = SpUtil.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		cb_box.setChecked(open_security);
		// 2.根据状态修改checkbox的文字显示
		if (open_security) {
			cb_box.setText("安全设置已开启");
		} else {
			cb_box.setText("安全设置已关闭");
		}
		// 3.点击过程checkbox的状态切换中，监听状态
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 4.isChecked点击后的状态，存储点击后状态
				SpUtil.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_SECURITY, isChecked);
				// 5.根据开启关闭状态，修改显示的文字
				if (isChecked) {
					cb_box.setText("安全设置已开启");
				} else {
					cb_box.setText("安全设置已关闭");
				}
			}
		});

	}

	/**
	 * 上页
	 * 
	 * @param v
	 */
/*	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(),
				Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);
	}*/

	/**
	 * 下页
	 * 
	 * @param view
	 */
/*	public void nextPage(View view) {
		boolean open_security = SpUtil.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			Intent intent = new Intent(getApplicationContext(),
					SetupOverActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
			SpUtil.putBoolean(this, ConstantValue.SETUP_OVER, true);

		} else {
			ToastUtil.show(getApplicationContext(), "请先开启防盗保护设置");
		}

	}*/

	@Override
	protected void showNextPage() {
		boolean open_security = SpUtil.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			Intent intent = new Intent(getApplicationContext(),
					SetupOverActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
			SpUtil.putBoolean(this, ConstantValue.SETUP_OVER, true);

		} else {
			ToastUtil.show(getApplicationContext(), "请先开启防盗保护设置");
		}

	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup3Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);
		
	}

}
