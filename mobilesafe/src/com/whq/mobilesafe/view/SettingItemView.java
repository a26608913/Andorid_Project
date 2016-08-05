package com.whq.mobilesafe.view;

import com.whq.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.whq.mobilesafe";
	private static final String tag = "SettingItemView";
	private TextView tv_des;
	private CheckBox cb_box;
	private String mDestitle;
	private String mDesoff;
	private String mDeson;

	public SettingItemView(Context context) {
		this(context, null);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// �Ѳ��ֵ������������ӵ���ǰ��Բ���SettingItemView��Ӧ��view��
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		/*
		 * View view = View.inflate(context, R.layout.activity_setting_item,
		 * null); this.addView(view);
		 */
		View.inflate(context, R.layout.activity_setting_item, this);
		//�Զ�����Ͽؼ��ı�������
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
		cb_box = (CheckBox) findViewById(R.id.cb_box);
		//��ȡ�Զ����Լ�ԭ�����ԵĲ�����д�ڴ˴��� AttributeSet attrs�����л�ȡ
		initAttrs(attrs);
		tv_title.setText(mDestitle);
	}

	private void initAttrs(AttributeSet attrs) {
		mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
		mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
		mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
/*		Log.i(tag, mDestitle);
		Log.i(tag, mDesoff);
		Log.i(tag, mDeson);*/
	}

	// ��ȡ����checkbox��״̬
	public boolean isCheck() {
		return cb_box.isChecked();
	}

	// activity_setting_item��Ŀ����checkbox״̬
	/**
	 * @param ischeck ��Ϊ�Ƿ����ı������ɵ��������ȥ������
	 */
	public void setCheck(boolean ischeck) {
		cb_box.setChecked(ischeck);
		if (ischeck) {
			tv_des.setText(mDeson);
		} else {
			tv_des.setText(mDesoff);
		}

	}
}
