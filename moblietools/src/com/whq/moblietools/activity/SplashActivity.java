package com.whq.moblietools.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.whq.moblietools.R;
import com.whq.moblietools.R.layout;
import com.whq.moblietools.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private static final String tag = "SplashActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_main);
		// 1.找到显示版本控件
		TextView tv_version_number = (TextView) findViewById(R.id.tv_version_number);
		tv_version_number.setText("版本名称:" + showVersionName());

		// 2.显示数据版本号和版本名称
		showData();
		newVersionUpdate();
	}

	/**
	 * 检测更新版本名称
	 */
	private void newVersionUpdate() {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(
							"http://10.0.2.2:8080/update_version.json");
					HttpURLConnection connection;

					connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(2000);
					connection.setReadTimeout(2000);
					
					if(connection.getResponseCode()==200){
						//以流的方式获取数据
						InputStream is = connection.getInputStream();
						//将流转换成字符串
						String jsonString = Utils.Strem2String(is);
						Log.i(tag, jsonString);
					}

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}.start();
	}

	/**
	 * 显示数据版本名称和版本号
	 */
	private void showData() {
		showVersionName();
		showVersionCode();
	}

	/**
	 * @return 返回版本号 返回非0为正常
	 */
	private int showVersionCode() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @return 返回版名称字符 如果非空为正常
	 */
	private String showVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
