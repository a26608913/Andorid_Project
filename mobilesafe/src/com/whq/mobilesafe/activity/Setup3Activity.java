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
		// 回显电话
		showPhone();
	}

	/**
	 * 在EditView从sp中回显电话号码
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
			// 返回到当前界面的时候，接受结果的方法
			mPhone = data.getStringExtra("phone");
			// 过滤特殊字符
			mPhone = mPhone.replace("-", "").replace(" ", "").trim();
			et_phone_number.setText(mPhone);
			// 存储联系人到sp中
			SpUtil.putString(getApplicationContext(), ConstantValue.PHONE_NUM,
					mPhone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 上页
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
	 * 下页
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
			// 如果电话号码是手输入的，也需要保存
			SpUtil.putString(getApplicationContext(), ConstantValue.PHONE_NUM,
					phone);
		} else {
			ToastUtil.show(getApplicationContext(), "请输入电话号码");
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
					// 如果电话号码是手输入的，也需要保存
					SpUtil.putString(getApplicationContext(), ConstantValue.PHONE_NUM,
							phone);
				} else {
					ToastUtil.show(getApplicationContext(), "请输入电话号码");
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
