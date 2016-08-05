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
		/*// 2.�������ƹ���Ķ�������������onTouchEvent(event)���ݹ��������ƶ���
		gestureDetector = new GestureDetector(getApplicationContext(),
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// �������Ƶ��ƶ�
						if (e1.getX() - e2.getX() > 0) {
							// ���ҵ����ƶ�����һҳ
							Intent intent = new Intent(getApplicationContext(),
									Setup2Activity.class);
							startActivity(intent);
							// ������һ���µ�Activity��͹رվɵ�
							finish();
							// ����ƽ�ƶ���
							overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
						}
						if (e1.getX() - e2.getX() < 0) {
							// �������ң��ƶ�����һҳ
						}

						return super.onFling(e1, e2, velocityX, velocityY);
					}

				});
		 */
	}

/*	public void nextPage(View v) {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		// ������һ���µ�Activity��͹رվɵ�
		finish();
		// ����ƽ�ƶ���
		overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
	}*/

/*	// 1.������Ļ����Ӧ���¼�����(����(1��)���ƶ�(���)��̧��(1��))
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 3.ͨ�����ƴ����࣬���ն������͵��¼�����������ķ���
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}*/

	@Override
	protected void showNextPage() {
		Intent intent = new Intent(getApplicationContext(),Setup2Activity.class);
		startActivity(intent);
		// ������һ���µ�Activity��͹رվɵ�
		finish();
		// ����ƽ�ƶ���
		overridePendingTransition(R.anim.next_in_translate,R.anim.next_out_translate);
	}

	@Override
	protected void showPrePage() {
		// û����һҳ����ʵ��
		
	}

}
