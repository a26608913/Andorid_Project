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
		// 第一种方法: 去除当前activity头title
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		// 初始化UI
		initUI();
		// 初始化数据
		initData();
	}

	/**
	 * 获取数据
	 */
	private void initData() {
		// 1.应用版本的名称
		tv_version_name.setText("版本名称:" + getVersionName());
		// 检测（本地版本号和服务器版本号比对）是否有更新，如果有更新，提示用户下载(member)
		// 2.应用的版本号
		mLocalVersionCode = getVersionCode();
		// 3.获取服务器版本号(客户端发请求，服务端给响应(json,xml)) http://ip/update.json?key=value
		// 返回200 请求成功,流方式数据读取下来
		/*
		 * json中内容 1.更新版本名称 2.新版本的描述 3.服务器版本号 4.新版本apk下载地址
		 */
		checkVersion();
	}

	private void checkVersion() {
		// 第一种方法开启线程
		new Thread() {
			public void run() {
				// 发送请求数据
				// 仅限于模拟器访问电脑Tomcat
				try {
					// 1.封装url地址
					URL url = new URL(
							"http://10.0.2.2:8080/update_version.json");
					// 2.开启一个链接
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();

					//3.设置常见参数(请求头)
					//请求超时
					connection.setConnectTimeout(2000);
					//读取超时
					connection.setReadTimeout(2000);
					//请求方式 默认为get
					//connection.setRequestMethod("POST");
					//4.获取响应码
					if(connection.getResponseCode()==200){
						//5.以流的形式，装数据获取下来
						InputStream is = connection.getInputStream();
						//6.将流转换成字符串
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
		 * 第二种方法通过接口方式写线程 new Thread(new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * } }){}.start();
		 */
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	private int getVersionCode() {
		// 1.获取包管理者对象
		PackageManager pm = getPackageManager();
		try {
			// 2.从包管理者对象获取指定包名基本名称，（版本号，传0代表获取基本信息)
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 3.获取返回版本号
			return packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取版本名称：从清单文件中
	 * 
	 * @return 应用版本名称 返回null代表有异常
	 */
	private String getVersionName() {
		// 1.包管理者对象packageManger
		PackageManager pm = getPackageManager();
		// 2.从包的管理者pm对象中，获取指定包名的基本信息(版本名称，版本号),0代表获取基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 3.获取版本名称
			return packageInfo.versionName;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化UI方法 alt+shift+j
	 */
	private void initUI() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);

	}

}
