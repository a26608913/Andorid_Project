package com.whq.mobilesafe.activity;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;
import com.whq.mobilesafe.utils.ToastUtil;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gv_home;
	private String[] mTitleStrs;
	private int[] mDarwableIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// ��ʼ������
		initUI();
		// ��ʼ������
		initData();
	}

	/**
	 * ��ʼ���ؼ�����
	 */
	private void initData() {
		mTitleStrs = new String[] { "�ֻ�����", "ͨ����ʿ", "�������", "���̹���", "����ͳ��",
				"�ֻ�ɱ��", "��������", "�߼�����", "��������" };

		mDarwableIds = new int[] { R.drawable.home_safe,
				R.drawable.home_callmsgsafe, R.drawable.home_callmsgsafe,
				R.drawable.home_apps, R.drawable.home_taskmanager,
				R.drawable.home_netmanager, R.drawable.home_trojan,
				R.drawable.home_sysoptimize, R.drawable.home_tools,
				R.drawable.home_settings };
		// �Ź���ؼ���������������
		gv_home.setAdapter(new MyAdapter());

		// ע��Ź���ĵ���¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {
			// position:�����б���Ŀ����
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0://
						// �����Ի���
					showDialog();
					break;
				case 8:// ��������
					Intent intent = new Intent(getApplicationContext(),
							SettingActivity.class);
					startActivity(intent);
					break;
				}

			}
		});
	}

	/**
	 * 
	 */
	protected void showDialog() {
		// �жϱ����Ƿ��д洢����(sp �ַ���)
		String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
		if (TextUtils.isEmpty(psd)) {
			// 1.��ʼ��������Ի���
			showSetPsdDialog();
		} else {
			// 2.ȷ������Ի���
			showConfirmPsdDialog();
		}

	}

	/**
	 * ȷ������Ի���
	 */
	private void showConfirmPsdDialog() {
		// ��Ϊ��Ҫ�Լ�����Ի����չʾ��ʽ��������Ҫ��dialog.setView(view);
		// view���Լ���д��xmlת���ɵ�view����xml---->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();

		final View view = View.inflate(this, R.layout.dialog_confrim_psd, null);
		// �öԻ�����ʾһ���Լ�����ĶԻ������Ч��
		// dialog.setView(view);
		// Ϊ�˼��ݵͰ汾,�߾�Ч��
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		bt_submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				EditText et_confirm_psd = (EditText) view
						.findViewById(R.id.et_confirm_psd);
				String confirm_psd = et_confirm_psd.getText().toString();

				if (!TextUtils.isEmpty(confirm_psd)) {
					String psd = SpUtil.getString(getApplicationContext(),ConstantValue.MOBILE_SAFE_PSD, "");
					if (psd.equals(confirm_psd)) {
						// �����ֻ�����ģ�飬����һ���µ�Activity
						Intent intent = new Intent(getApplicationContext(),
								TestActivity.class);
						startActivity(intent);
						// ��ת���µĽ������Ҫ���ضԻ���
						dialog.dismiss();

					} else {
						ToastUtil.show(getApplicationContext(), "ȷ���������");
					}

				} else {
					// ��ʾ�û�������Ϊ�յ����
					ToastUtil.show(getApplicationContext(), "����������");
				}
			}
		});

		bt_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

	/**
	 * ��������Ի���
	 */
	private void showSetPsdDialog() {
		// ��Ϊ��Ҫ�Լ�����Ի����չʾ��ʽ��������Ҫ��dialog.setView(view);
		// view���Լ���д��xmlת���ɵ�view����xml---->view
		Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();

		final View view = View.inflate(this, R.layout.dialog_set_psd, null);
		// �öԻ�����ʾһ���Լ�����ĶԻ������Ч��
		// dialog.setView(view);
		// Ϊ�˼��ݵͰ汾,�߾�Ч��
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

		Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
		Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
		bt_submit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				EditText et_set_psd = (EditText) view
						.findViewById(R.id.et_set_psd);
				EditText et_confirm_psd = (EditText) view
						.findViewById(R.id.et_confirm_psd);

				String psd = et_set_psd.getText().toString();
				String confirm_psd = et_confirm_psd.getText().toString();

				if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirm_psd)) {
					if (psd.equals(confirm_psd)) {
						// �����ֻ�����ģ�飬����һ���µ�Activity
						Intent intent = new Intent(getApplicationContext(),
								TestActivity.class);
						startActivity(intent);
						// ��ת���µĽ������Ҫ���ضԻ���
						dialog.dismiss();
						SpUtil.putString(getApplicationContext(),
								ConstantValue.MOBILE_SAFE_PSD, psd);

					} else {
						ToastUtil.show(getApplicationContext(), "ȷ���������");
					}

				} else {
					// ��ʾ�û�������Ϊ�յ����
					ToastUtil.show(getApplicationContext(), "����������");
				}

			}
		});

		bt_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

	/**
	 * ���ؿؼ�
	 */
	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mTitleStrs.length;
		}

		@Override
		public Object getItem(int position) {
			return mTitleStrs[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.gridview_item, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			iv_icon.setBackgroundResource(mDarwableIds[position]);
			tv_title.setText(mTitleStrs[position]);
			return view;
		}

	}

}
