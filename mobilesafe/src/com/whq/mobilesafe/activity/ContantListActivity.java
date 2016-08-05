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
			// 联系人集合的大小
			return contactList.size();
		}

		@Override
		public HashMap<String, String> getItem(int position) {
			// 联系人对应的集合
			return contactList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// 返回id
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
	 * 加载系统通讯录数据
	 */
	private void initData() {
		/*
		 * //打开系统联系人查找 Intent intent = new Intent(Intent.ACTION_PICK,
		 * android.provider.ContactsContract.Contacts.CONTENT_URI);
		 * startActivityForResult(intent, 1); //在返回的方法中处理，获得联系人的信息，姓名，电话 Uri uri
		 * = intent.getData(); //得到ContentResolver对象 ContentResolver resolver =
		 * getContentResolver(); //取得电话本中开始一项的光标 Cursor cursor =
		 * resolver.query(uri, null, null, null, null); //向下移动光标 while
		 * (cursor.moveToNext()) { int columnIndex =
		 * cursor.getColumnIndex(Contacts.DISPLAY_NAME); String name =
		 * cursor.getString(columnIndex);
		 * System.out.println("name---------------------------"+name);; }
		 */
		/**
		 * @author 子线程查询通讯录 由于可能是耗时操作，所以放子线程
		 */
		new Thread() {
			public void run() {
				Uri uri = Uri
						.parse("content://com.android.contacts/raw_contacts");
				// 1.获取内容解析器对象
				ContentResolver contentResolver = getContentResolver();
				// 2.做查询系统联系人数据表过程(读取联系人权限 )
				Cursor cursor = contentResolver.query(uri,
						new String[] { "contact_id" }, null, null, null);
				contactList.clear();
				// 3.循环游标,直到没有数据为止
				while (cursor.moveToNext()) {
					String id = cursor.getString(0);
					Log.i(tag, "id:---------" + id);
					// 4.根据用户唯一性id值,查询data表和mimetype表生成的视图,获取data以及mimetype字段
					Cursor indexCursor = contentResolver.query(
							Uri.parse("content://com.android.contacts/data"),
							new String[] { "data1", "mimetype" },
							"raw_contact_id = ?", new String[] { id }, null);

					HashMap<String, String> hashMap = new HashMap<String, String>();

					// 5.循环获取每一个联系人的电话号以及姓名
					while (indexCursor.moveToNext()) {
						String data = indexCursor.getString(0);
						String type = indexCursor.getString(1);
						// 6.区分类型去给hashMap填充数据
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
				// 7.消息机制,发送一个空的消息，告知主线程可以使用子线程已经填充好的数据集合
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