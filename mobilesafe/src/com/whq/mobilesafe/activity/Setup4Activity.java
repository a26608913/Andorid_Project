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
		// 1.�Ƿ�ѡ��״̬������
		boolean open_security = SpUtil.getBoolean(getApplicationContext(),
				ConstantValue.OPEN_SECURITY, false);
		cb_box.setChecked(open_security);
		// 2.����״̬�޸�checkbox��������ʾ
		if (open_security) {
			cb_box.setText("��ȫ�����ѿ���");
		} else {
			cb_box.setText("��ȫ�����ѹر�");
		}
		// 3.�������checkbox��״̬�л��У�����״̬
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 4.isChecked������״̬���洢�����״̬
				SpUtil.putBoolean(getApplicationContext(),
						ConstantValue.OPEN_SECURITY, isChecked);
				// 5.���ݿ����ر�״̬���޸���ʾ������
				if (isChecked) {
					cb_box.setText("��ȫ�����ѿ���");
				} else {
					cb_box.setText("��ȫ�����ѹر�");
				}
			}
		});

	}

	/**
	 * ��ҳ
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
	 * ��ҳ
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
			ToastUtil.show(getApplicationContext(), "���ȿ���������������");
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
			ToastUtil.show(getApplicationContext(), "���ȿ���������������");
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
