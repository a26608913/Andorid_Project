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

	// 把布局单独抽出来，添加到当前相对布局SettingItemView对应的view中
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		/*
		 * View view = View.inflate(context, R.layout.activity_setting_item,
		 * null); this.addView(view);
		 */
		View.inflate(context, R.layout.activity_setting_item, this);
		//自定义组合控件的标题描述
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
		cb_box = (CheckBox) findViewById(R.id.cb_box);
		//获取自定义以及原生属性的操作，写在此处， AttributeSet attrs对象中获取
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

	// 获取返回checkbox的状态
	public boolean isCheck() {
		return cb_box.isChecked();
	}

	// activity_setting_item条目关联checkbox状态
	/**
	 * @param ischeck 作为是否开启的变量，由点击过程中去做传递
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
