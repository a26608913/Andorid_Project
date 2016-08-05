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
	 * ��SIM�� �Զ�����Ƶĵ���¼�
	 */
	private void initUI() {
		siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
		// 1.����(��ȡ���еİ�״̬����ʾsp���Ƿ�洢��sim�������к�)
		String sim_number = SpUtil
				.getString(this, ConstantValue.SIM_NUMBER, "");
		// 2.�ж����к��Ƿ�Ϊ��
		if (TextUtils.isEmpty(sim_number)) {
			siv_sim_bound.setCheck(false);
		} else {
			siv_sim_bound.setCheck(true);
		}

		siv_sim_bound.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 3.��ȡԭ�е�״̬
				boolean ischeck = siv_sim_bound.isCheck();
				// 4.��ԭ��״̬ȡ����״̬���ø���ǰ��Ŀ
				siv_sim_bound.setCheck(!ischeck);
				// 5.�������п��Ÿ���ǰ��Ŀ
				if (!ischeck) {
					// 6.�洢
					// 6.1��ȡsim������к�
					TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String simSerialNumber = manager.getSimSerialNumber();
					SpUtil.putString(getApplicationContext(),
							ConstantValue.SIM_NUMBER, simSerialNumber);
				} else {
					// 7.Ĩ��
					SpUtil.removeString(getApplicationContext(),
							ConstantValue.SIM_NUMBER);
				}

			}
		});
	}

	/**
	 * ��һҳ
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
	 * ��һҳ
	 * 
	 * @param v
	 */
/*	public void nextPage(View v) {
		//��SIM���˲�����һҳ
		String serialNumber = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER, "");
		if (!TextUtils.isEmpty(serialNumber)) {
			Intent intent = new Intent(getApplicationContext(),
					Setup3Activity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
		}else {
			ToastUtil.show(getApplicationContext(), "���ȵ����SIM��");
		}
	}
	*/
/*	//1.�����¼�
	public boolean onTouchEvent(MotionEvent event) {
		//3.ʹ�����ƹ�����
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
			ToastUtil.show(getApplicationContext(), "���ȵ����SIM��");
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
