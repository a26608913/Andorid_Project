package com.whq.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.whq.mobilesafe.R;

public class ContantListActivity extends Activity {
	private ListView lv_contact;
	protected static final String tag = "ContantListActivity";
	private List<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
	private MyAdapter mAdapter;
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			mAdapter = new MyAdapter();
			lv_contact.setAdapter(mAdapter);

		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);
		initUI();
		initData();
	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// ��ϵ�˼��ϵĴ�С
			return contactList.size();
		}

		@Override
		public HashMap<String, String> getItem(int position) {
			// ��ϵ�˶�Ӧ�ļ���
			return contactList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// ����id
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.lv_contact_item, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

			tv_name.setText(getItem(position).get("name"));
			tv_phone.setText(getItem(position).get("phone"));
			return view;
		}

	}

	/**
	 * ����ϵͳͨѶ¼����
	 */
	private void initData() {
		/*
		 * //��ϵͳ��ϵ�˲��� Intent intent = new Intent(Intent.ACTION_PICK,
		 * android.provider.ContactsContract.Contacts.CONTENT_URI);
		 * startActivityForResult(intent, 1); //�ڷ��صķ����д��������ϵ�˵���Ϣ���������绰 Uri uri
		 * = intent.getData(); //�õ�ContentResolver���� ContentResolver resolver =
		 * getContentResolver(); //ȡ�õ绰���п�ʼһ��Ĺ�� Cursor cursor =
		 * resolver.query(uri, null, null, null, null); //�����ƶ���� while
		 * (cursor.moveToNext()) { int columnIndex =
		 * cursor.getColumnIndex(Contacts.DISPLAY_NAME); String name =
		 * cursor.getString(columnIndex);
		 * System.out.println("name---------------------------"+name);; }
		 */
		/**
		 * @author ���̲߳�ѯͨѶ¼ ���ڿ����Ǻ�ʱ���������Է����߳�
		 */
		new Thread() {
			public void run() {
				Uri uri = Uri
						.parse("content://com.android.contacts/raw_contacts");
				// 1.��ȡ���ݽ���������
				ContentResolver contentResolver = getContentResolver();
				// 2.����ѯϵͳ��ϵ�����ݱ����(��ȡ��ϵ��Ȩ�� )
				Cursor cursor = contentResolver.query(uri,
						new String[] { "contact_id" }, null, null, null);
				contactList.clear();
				// 3.ѭ���α�,ֱ��û������Ϊֹ
				while (cursor.moveToNext()) {
					String id = cursor.getString(0);
					Log.i(tag, "id:---------" + id);
					// 4.�����û�Ψһ��idֵ,��ѯdata���mimetype�����ɵ���ͼ,��ȡdata�Լ�mimetype�ֶ�
					Cursor indexCursor = contentResolver.query(
							Uri.parse("content://com.android.contacts/data"),
							new String[] { "data1", "mimetype" },
							"raw_contact_id = ?", new String[] { id }, null);

					HashMap<String, String> hashMap = new HashMap<String, String>();

					// 5.ѭ����ȡÿһ����ϵ�˵ĵ绰���Լ�����
					while (indexCursor.moveToNext()) {
						String data = indexCursor.getString(0);
						String type = indexCursor.getString(1);
						// 6.��������ȥ��hashMap�������
						if (type.equals("vnd.android.cursor.item/phone_v2")) {
							if (!TextUtils.isEmpty(data)) {
								hashMap.put("phone", data);
							}
						} else if (type.equals("vnd.android.cursor.item/name")) {
							if (!TextUtils.isEmpty(data)) {
								hashMap.put("name", data);
							}
						}
					}
					indexCursor.close();
					contactList.add(hashMap);
				}
				cursor.close();
				// 7.��Ϣ����,����һ���յ���Ϣ����֪���߳̿���ʹ�����߳��Ѿ����õ����ݼ���
				mHandler.sendEmptyMessage(0);
			};

		}.start();

	}

	private void initUI() {
		lv_contact = (ListView) findViewById(R.id.lv_contact);
		lv_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mAdapter != null) {
					HashMap<String, String> hashMap = mAdapter.getItem(position);
					String phone = hashMap.get("phone");
					Intent intent = new Intent();
					intent.putExtra("phone", phone);
					setResult(0, intent);
					finish();
				}

			}
		});

	}
}