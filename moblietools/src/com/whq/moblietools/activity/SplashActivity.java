package com.whq.moblietools.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.whq.moblietools.R;
import com.whq.moblietools.R.layout;
import com.whq.moblietools.utils.ToastUtil;
import com.whq.moblietools.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private static final String tag = "SplashActivity";
	protected static final int UPDATE_VERSION = 100;
	protected static final int ENTER_HOME = 101;
	protected static final int URL_ERROR = 102;
	protected static final int IO_ERROR = 103;
	protected static final int JSON_ERROR = 104;
	private String mDownloadUrl;
	private String mVersionDes;
	private int mLocalVersionCode;
	private Handler mHandler = new Handler() {
		// 不要用自定义方法，必需要用handlerMessage()方法才有效果
		public void handleMessage(android.os.Message msg) {
			// 收到状态码做判断
			switch (msg.what) {
			case UPDATE_VERSION:
				// 显示升级对话框
				showUpdateDialog();
				break;
			case ENTER_HOME:
				enter_home();
				finish();
				break;
			case URL_ERROR:
				ToastUtil.show(getApplicationContext(), "url异常");
				enter_home();
				break;
			case IO_ERROR:
				ToastUtil.show(getApplicationContext(), "网络读取异常");
				enter_home();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json异常");
				enter_home();
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_main);
		// 1.找到显示版本控件
		TextView tv_version_number = (TextView) findViewById(R.id.tv_version_number);
		tv_version_number.setText("版本名称:" + showVersionName());

		// 2.显示数据版本号和版本名称
		showData();
		// 检查新版本升级
		checkVersionUpdate();
	}

	/**
	 * 显示升级对话框
	 */
	protected void showUpdateDialog() {
		Builder builder = new AlertDialog.Builder(this);
		// 显示图标
		builder.setIcon(R.drawable.ic_launcher);
		// 显示标题
		builder.setTitle("更新版本");
		// 显示描述信息
		builder.setMessage(mVersionDes);
		// 显示更新、稍后逻辑
		builder.setPositiveButton("更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 更新替换程序
				downloadApk();
			}
		});

		builder.setNegativeButton("稍后", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取消更新,进入主界面
				enter_home();
			}
		});

		builder.setOnCancelListener(new OnCancelListener() {
			// 点击取消按钮，进入主界面
			public void onCancel(DialogInterface dialog) {
				enter_home();
				dialog.dismiss();
			}
		});
		builder.show();

	}

	/**
	 * 更新安装apk
	 */
	protected void downloadApk() {
		// 1.判断sd卡是否挂载
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// 2.使用XUtils框架下载
			HttpUtils httpUtils = new HttpUtils();
			// 获取sd卡路径
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "mobiletool.apk";
			Log.i(tag, "======================="+path+"============="+mDownloadUrl+"===========");
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {

				// 准备开始下载
				public void onStart() {

					Log.i(tag, "准备开始下载......");
					super.onStart();
				}

				// 下载中
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.i(tag, "total:" + total);
					Log.i(tag, "下载中..................");
					Log.i(tag, "current:" + current);
					super.onLoading(total, current, isUploading);
				}

				// 开始成功
				public void onSuccess(ResponseInfo<File> responseInfo) {
					File file = responseInfo.result;
					ToastUtil.show(getApplicationContext(), "已经下载到"
							+ responseInfo.result.getAbsolutePath());
					//提示用户安装
					installApk(file);
				}

				// 下载失败
				public void onFailure(HttpException arg0, String arg1) {
					Log.i(tag, "下载失败");
				}

			});
		}
	}
	
	/**
	 * 安装对应apk
	 * 使用系统风格
	 * @param file 下载的安装文件
	 */
	
	/*
	 *  <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="content" />
                <data android:scheme="file" />
                <data android:mimeType="application/vnd.android.package-archive" />
	 */
	protected void installApk(File file) {
		//系统应用界面，源码
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
	}
	//开启一个Activity后，返回结果调用方法
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enter_home();
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void enter_home() {
		// 进入程序主界面
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		//在开启一个新界面后，将导航界面关闭(导航界面只能见一次)
		finish();
	}

	/**
	 * 检测更新版本名称 从服务器获取json版本号
	 */
	private void checkVersionUpdate() {
		new Thread() {

			public void run() {
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					URL url = new URL(
							"http://192.168.1.108:8080/update_version.json");
					HttpURLConnection connection;
					connection = (HttpURLConnection) url.openConnection();
					connection.setConnectTimeout(2000);
					connection.setReadTimeout(2000);

					if (connection.getResponseCode() == 200) {
						// 以流的方式获取数据
						InputStream is = connection.getInputStream();
						// 将流转换成字符串
						String jsonString = Utils.Strem2String(is);
						// 解释json数据
						JSONObject jsonObject = new JSONObject(jsonString);
						String versionName = jsonObject
								.getString("versionName");
						String versionCode = jsonObject
								.getString("versionCode");
						mVersionDes = jsonObject.getString("versionDes");
						mDownloadUrl = jsonObject.getString("downloadUrl");
						// debug
						Log.i(tag, versionName);
						Log.i(tag, versionCode);
						Log.i(tag, mVersionDes);
						Log.i(tag, mDownloadUrl);
						// 判断如果服务器版本比本地版本高,就升级。否则,直接进入主界面
						if (mLocalVersionCode < Integer.parseInt(versionCode)) {
							// ☆☆☆要弹出对话框，子线程不能更新UI,要用消息机制Handler和msg对象
							msg.what = UPDATE_VERSION;
						} else {
							// 进入主界面
							msg.what = ENTER_HOME;
						}
					}
				} catch (MalformedURLException e) {
					// url异常
					msg.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					// 网络读取异常
					msg.what = IO_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					// json异常
					msg.what = JSON_ERROR;
					e.printStackTrace();
				} finally {
					long endTime = System.currentTimeMillis();
					// 强制停留4秒
					if (endTime - startTime < 4000) {
						try {
							Thread.sleep(4000 - (endTime - startTime));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
				}

			}

		}.start();
	}

	/**
	 * 显示数据版本名称和版本号
	 */
	private void showData() {
		showVersionName();
		mLocalVersionCode = showVersionCode();
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
