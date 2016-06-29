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
		tv_version_name.setText("版本名称:"+getVersionName());
		//2.检测 是否有版本更新，如果有更新，提示用户下载(本地版本号和服务器版本号比对)
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
