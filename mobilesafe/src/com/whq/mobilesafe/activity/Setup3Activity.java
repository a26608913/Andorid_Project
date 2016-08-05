package com.whq.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;
import com.whq.mobilesafe.utils.ToastUtil;

public class Setup3Activity extends BaseSetupActivity {
	private EditText et_phone_number;
	private String mPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_three);

		initUI();
		// ���Ե绰
		showPhone();
	}

	/**
	 * ��EditView��sp�л��Ե绰����
	 */
	private void showPhone() {
		String rephone = SpUtil.getString(getApplicationContext(),
				ConstantValue.PHONE_NUM, "");
		et_phone_number.setText(rephone);
	}

	private void initUI() {
		et_phone_number = (EditText) findViewById(R.id.et_phone_number);
		Button bt_select_number = (Button) findViewById(R.id.bt_select_number);
		bt_select_number.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ContantListActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			// ���ص���ǰ�����ʱ�򣬽��ܽ���ķ���
			mPhone = data.getStringExtra("phone");
			// ���������ַ�
			mPhone = mPhone.replace("-", "").replace(" ", "").trim();
			et_phone_number.setText(mPhone);
			// �洢��ϵ�˵�sp��
			SpUtil.putString(getApplicationContext(), ConstantValue.PHONE_NUM,
					mPhone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ��ҳ
	 * 
	 * @param v
	 */
/*	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(),
				Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);
	}*/

	/**
	 * ��ҳ
	 * 
	 * @param v
	 */
/*	public void nextPage(View v) {
		// String phone_num = SpUtil.getString(getApplicationContext(),
		// ConstantValue.PHONE_NUM, "");
		String phone = et_phone_number.getText().toString();
		if (!TextUtils.isEmpty(phone)) {
			Intent intent = new Intent(getApplicationContext(),
					Setup4Activity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
			// ����绰������������ģ�Ҳ��Ҫ����
			SpUtil.putString(getApplicationContext(), ConstantValue.PHONE_NUM,
					phone);
		} else {
			ToastUtil.show(getApplicationContext(), "������绰����");
		}

	}
*/
	@Override
	protected void showNextPage() {
		// String phone_num = SpUtil.getString(getApplicationContext(),
				// ConstantValue.PHONE_NUM, "");
				String phone = et_phone_number.getText().toString();
				if (!TextUtils.isEmpty(phone)) {
					Intent intent = new Intent(getApplicationContext(),
							Setup4Activity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
					// ����绰������������ģ�Ҳ��Ҫ����
					SpUtil.putString(getApplicationContext(), ConstantValue.PHONE_NUM,
							phone);
				} else {
					ToastUtil.show(getApplicationContext(), "������绰����");
				}

	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);
		
	}
}
