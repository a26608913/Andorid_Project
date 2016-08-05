package com.whq.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;
import com.whq.mobilesafe.utils.ToastUtil;
import com.whq.mobilesafe.view.SettingItemView;

public class Setup2Activity extends BaseSetupActivity {
	private SettingItemView siv_sim_bound;
	private GestureDetector gestureDetector;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_two);
		initUI();
	}
	/**
	 * 绑定SIM卡 自定义控制的点击事件
	 */
	private void initUI() {
		siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
		// 1.回显(读取已有的绑定状态，显示sp中是否存储了sim卡的序列号)
		String sim_number = SpUtil
				.getString(this, ConstantValue.SIM_NUMBER, "");
		// 2.判断序列号是否为空
		if (TextUtils.isEmpty(sim_number)) {
			siv_sim_bound.setCheck(false);
		} else {
			siv_sim_bound.setCheck(true);
		}

		siv_sim_bound.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 3.获取原有的状态
				boolean ischeck = siv_sim_bound.isCheck();
				// 4.将原有状态取反，状态设置给当前条目
				siv_sim_bound.setCheck(!ischeck);
				// 5.设置序列卡号给当前条目
				if (!ischeck) {
					// 6.存储
					// 6.1获取sim卡序号列号
					TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String simSerialNumber = manager.getSimSerialNumber();
					SpUtil.putString(getApplicationContext(),
							ConstantValue.SIM_NUMBER, simSerialNumber);
				} else {
					// 7.抹掉
					SpUtil.removeString(getApplicationContext(),
							ConstantValue.SIM_NUMBER);
				}

			}
		});
	}

	/**
	 * 上一页
	 * 
	 * @param v
	 */
/*	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(),
				Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);
	}*/

	/**
	 * 下一页
	 * 
	 * @param v
	 */
/*	public void nextPage(View v) {
		//当SIM绑定了才能下一页
		String serialNumber = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER, "");
		if (!TextUtils.isEmpty(serialNumber)) {
			Intent intent = new Intent(getApplicationContext(),
					Setup3Activity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
		}else {
			ToastUtil.show(getApplicationContext(), "请先点击绑定SIM卡");
		}
	}
	*/
/*	//1.触摸事件
	public boolean onTouchEvent(MotionEvent event) {
		//3.使用手势工具类
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}*/

	@Override
	protected void showNextPage() {
		String serialNumber = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER, "");
		if (!TextUtils.isEmpty(serialNumber)) {
			Intent intent = new Intent(getApplicationContext(),
					Setup3Activity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
		}else {
			ToastUtil.show(getApplicationContext(), "请先点击绑定SIM卡");
		}
	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);
		
	}
}
