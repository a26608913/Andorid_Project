package com.whq.mobilesafe.activity;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.SpUtil;
import com.whq.mobilesafe.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class BaseSetupActivity extends Activity {
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//2.�������ƶ�������������ontouchEvent(event)���ݹ��������ƶ���
		gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if(e1.getX()-e2.getX()>0){
					//��һҳ
					//�����������һҳ����������ķ���
					/*
					 * �����������һҳ����
					 * �ڵ�һ��ҳ���ϵ�ʱ����ת���ڶ���ҳ��
					 * �ڵڶ���ҳ���ϵ�ʱ����ת��������ҳ��
					 * ......
					 */
					showNextPage();
				}
				
				
				if (e1.getX()-e2.getX()<0) {
					//��һҳ
					/*
					 * �����������һҳ����
					 * �ڵ�һ��ҳ���ϵ�ʱ������Ӧ����ʵ��
					 * �ڵڶ���ҳ���ϵ�ʱ����ת����һ��ҳ��
					 * ......
					 */
					showPrePage();
					
				}
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	
	}
	

	/*public abstract void nextPage(View view){
		//����
	}*/



	// 1.������Ļ����Ӧ���¼�����(����(1��)���ƶ�(���)��̧��(1��))
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//3.ͨ�����ƴ����࣬���ն������͵��¼�����������
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	
	//��һҳ�ĳ��󷽷��������������ת���ĸ�����
	protected abstract void showNextPage();
	//��һҳ�ĳ��󷽷��������������ת���ĸ�����
	protected abstract void showPrePage();
	

	//��� ��һҳ��ť��ʱ�򣬸��������showPrePage()��������Ӧ����ת
	public void prePage(View v) {
		
		showPrePage();
		/*Intent intent = new Intent(getApplicationContext(),
				Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);*/
	}

	/**
	 * ��һҳ
	 * 
	 * @param v
	 */
	public void nextPage(View v) {
		
		showNextPage();
	}
/*		//��SIM���˲�����һҳ
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
	}*/

	
}
