package com.whq.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Administrator
 * �ܹ���ȡ������Զ���TextView
 */
public class FoucsTextView extends TextView {
	// ��JAVA�����д����ؼ�
	public FoucsTextView(Context context) {
		super(context);
	}

	// ��ϵͳ����(������+�������Ļ������췽��)
	public FoucsTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// ��ϵͳ���ã�������+�����Ļ���+�����ļ��ж�����ʽ�ļ����췽��)
	public FoucsTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	//��д��ȡ����ķ���,��ϵͳ���ã�����ʱĬ�Ͼ��ܻ�ȡ����
	@Override
	public boolean isFocused() {
		return true;
	}

}
