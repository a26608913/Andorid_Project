package com.whq.mobilesafe.activity;

import com.whq.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class Setup1Activity extends BaseSetupActivity {
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup_one);
		/*// 2.创建手势管理的对象，用作管理在onTouchEvent(event)传递过来的手势动作
		gestureDetector = new GestureDetector(getApplicationContext(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// 监听手势的移动
						if (e1.getX() - e2.getX() > 0) {
							// 由右到左，移动到下一页
							Intent intent = new Intent(getApplicationContext(),
									Setup2Activity.class);
							startActivity(intent);
							// 开启了一个新的Activity后就关闭旧的
							finish();
							// 开启平移动画
							overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
						}
						if (e1.getX() - e2.getX() < 0) {
							// 由左向右，移动到上一页
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}

				});
		 */
	}

/*	public void nextPage(View v) {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		// 开启了一个新的Activity后就关闭旧的
		finish();
		// 开启平移动画
		overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
	}*/

/*	// 1.监听屏幕上响应的事件类型(接下(1次)，移动(多次)，抬起(1次))
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 3.通过手势处理类，接收多种类型的事件，用作处理的方法
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}*/

	@Override
	protected void showNextPage() {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		// 开启了一个新的Activity后就关闭旧的
		finish();
		// 开启平移动画
		overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
	}

	@Override
	protected void showPrePage() {
		// 没有上一页，空实现
		
	}

}
