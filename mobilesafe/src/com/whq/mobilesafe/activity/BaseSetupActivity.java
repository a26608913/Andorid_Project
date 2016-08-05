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
		//2.创建手势对象，用作管理在ontouchEvent(event)传递过来的手势动作
		gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if(e1.getX()-e2.getX()>0){
					//下一页
					//调用子类的下一页方法，抽象的方法
					/*
					 * 调用子类的上一页方法
					 * 在第一个页面上的时候，跳转到第二个页面
					 * 在第二个页面上的时候，跳转到第三个页面
					 * ......
					 */
					showNextPage();
				}
				
				
				if (e1.getX()-e2.getX()<0) {
					//上一页
					/*
					 * 调用子类的上一页方法
					 * 在第一个页面上的时候，无响应，空实现
					 * 在第二个页面上的时候，跳转到第一个页面
					 * ......
					 */
					showPrePage();
					
				}
				
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	
	}
	

	/*public abstract void nextPage(View view){
		//抽象
	}*/



	// 1.监听屏幕上响应的事件类型(接下(1次)，移动(多次)，抬起(1次))
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//3.通过手势处理类，接收多种类型的事件，用作处理
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	
	//下一页的抽象方法，由子类决定跳转到哪个界面
	protected abstract void showNextPage();
	//上一页的抽象方法，由子类决定跳转到哪个界面
	protected abstract void showPrePage();
	

	//点击 上一页按钮的时候，根据子类的showPrePage()方法做相应的跳转
	public void prePage(View v) {
		
		showPrePage();
		/*Intent intent = new Intent(getApplicationContext(),
				Setup1Activity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in_translate, R.anim.pre_out_translate);*/
	}

	/**
	 * 下一页
	 * 
	 * @param v
	 */
	public void nextPage(View v) {
		
		showNextPage();
	}
/*		//当SIM绑定了才能下一页
		String serialNumber = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUMBER, "");
		if (!TextUtils.isEmpty(serialNumber)) {
			Intent intent = new Intent(getApplicationContext(),
					Setup3Activity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.next_in_translate, R.anim.next_out_translate);
		}else {
			ToastUtil.show(getApplicationContext(), "请先点击绑定SIM卡");
		}
	}*/

	
}
