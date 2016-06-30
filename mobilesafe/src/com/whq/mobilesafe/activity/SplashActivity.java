package com.whq.mobilesafe.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.R.layout;
import com.whq.mobilesafe.R.menu;
import com.whq.mobilesafe.utils.StreamUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final String tag = "SplashActivity";
	private TextView tv_version_name;
	private int mLocalVersionCode;

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
		tv_version_name.setText("�汾����:" + getVersionName());
		// ��⣨���ذ汾�źͷ������汾�űȶԣ��Ƿ��и��£�����и��£���ʾ�û�����(member)
		// 2.Ӧ�õİ汾��
		mLocalVersionCode = getVersionCode();
		// 3.��ȡ�������汾��(�ͻ��˷����󣬷���˸���Ӧ(json,xml)) http://ip/update.json?key=value
		// ����200 ����ɹ�,����ʽ���ݶ�ȡ����
		/*
		 * json������ 1.���°汾���� 2.�°汾������ 3.�������汾�� 4.�°汾apk���ص�ַ
		 */
		checkVersion();
	}

	private void checkVersion() {
		// ��һ�ַ��������߳�
		new Thread() {
			public void run() {
				// ������������
				// ������ģ�������ʵ���Tomcat
				try {
					// 1.��װurl��ַ
					URL url = new URL(
							"http://10.0.2.2:8080/update_version.json");
					// 2.����һ������
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();

					//3.���ó�������(����ͷ)
					//����ʱ
					connection.setConnectTimeout(2000);
					//��ȡ��ʱ
					connection.setReadTimeout(2000);
					//����ʽ Ĭ��Ϊget
					//connection.setRequestMethod("POST");
					//4.��ȡ��Ӧ��
					if(connection.getResponseCode()==200){
						//5.��������ʽ��װ���ݻ�ȡ����
						InputStream is = connection.getInputStream();
						//6.����ת�����ַ���
						String jsonString = StreamUtil.stream2String(is);
						Log.i(tag, jsonString);
					}
					
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			};

		}.start();

		/*
		 * �ڶ��ַ���ͨ���ӿڷ�ʽд�߳� new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * } }){}.start();
		 */
	}

	/**
	 * ��ȡ�汾��
	 * 
	 * @return
	 */
	private int getVersionCode() {
		// 1.��ȡ�������߶���
		PackageManager pm = getPackageManager();
		try {
			// 2.�Ӱ������߶����ȡָ�������������ƣ����汾�ţ���0�����ȡ������Ϣ)
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 3.��ȡ���ذ汾��
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
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
