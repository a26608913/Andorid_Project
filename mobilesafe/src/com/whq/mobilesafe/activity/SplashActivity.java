package com.whq.mobilesafe.activity;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.R.layout;
import com.whq.mobilesafe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private TextView tv_version_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��һ�ַ���: ȥ����ǰactivityͷtitle
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		// ��ʼ��UI
		initUI();
		// ��ʼ������
		initData();
	}

	/**
	 * ��ȡ����
	 */
	private void initData() {
		// 1.Ӧ�ð汾������
		tv_version_name.setText("�汾����:"+getVersionName());
		//2.��� �Ƿ��а汾���£�����и��£���ʾ�û�����(���ذ汾�źͷ������汾�űȶ�)
	}

	
	/**
	 * ��ȡ�汾���ƣ����嵥�ļ���
	 * 
	 * @return Ӧ�ð汾���� ����null�������쳣
	 */
	private String getVersionName() {
		// 1.�������߶���packageManger
		PackageManager pm = getPackageManager();
		// 2.�Ӱ��Ĺ�����pm�����У���ȡָ�������Ļ�����Ϣ(�汾���ƣ��汾��),0�����ȡ������Ϣ
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 3.��ȡ�汾����
			return packageInfo.versionName;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��UI���� alt+shift+j
	 */
	private void initUI() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);

	}

}
