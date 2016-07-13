package com.whq.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.whq.mobilesafe.R;
import com.whq.mobilesafe.utils.StreamUtil;
import com.whq.mobilesafe.utils.ToastUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final String tag = "SplashActivity";
	/**
	 * 更新新版本的状态码
	 */
	protected static final int UPDATE_VERSION = 100;
	/**
	 * 进入主界面的状态码
	 */
	protected static final int ENTER_HOME = 101;
	/**
	 * URL异常状态码
	 */
	protected static final int URL_ERROR = 102;
	/**
	 * IO异常状态码
	 */
	protected static final int IO_ERROR = 103;
	/**
	 * JSON异常状态码
	 */
	protected static final int JSON_ERROR = 104;

	private TextView tv_version_name;
	private int mLocalVersionCode;
	private String mVersionDes;
	private String mDownloadUrl;
	// 成员变量都要m开头，保持和源码一致辞
	private Handler mHandler = new Handler() {
		// 不要自定义方法，必需要用handleMessage()才有效果
		public void handleMessage(Message msg) {
			// 收到状态码做判断
			switch (msg.what) {
			case UPDATE_VERSION:
				// 弹出对话框，提示用户更新
				showUpdateDialog();
				break;
			case ENTER_HOME:
				// 进入主界面
				enterHome();
				break;
			case URL_ERROR:
				// 这些异常是打包给自己看，上线时记得删除
				ToastUtil.show(getApplicationContext(), "url异常");
				enterHome();
				break;
			case IO_ERROR:
				ToastUtil.show(getApplicationContext(), "io读取异常");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json解释异常");
				enterHome();
				break;
			}

		};

	};
	private RelativeLayout rl_root;

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
		// 初始化动画
		initAnimation();
	}

	/**
	 * 设置淡入的动画效果
	 */
	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000 * 5);
		rl_root.startAnimation(alphaAnimation);
	}

	/**
	 * 弹出对话框，提示用户更新
	 */
	protected void showUpdateDialog() {
		// 对话框是依赖于Activity存在的,不能用getApplicationContext()
		Builder builder = new AlertDialog.Builder(this);
		// 设置左上角图标
		builder.setIcon(R.drawable.ic_launcher);
		// 设置标题
		builder.setTitle("版本更新");
		// 设置描述内存
		builder.setMessage(mVersionDes);
		// 积极按键，立即更新
		builder.setPositiveButton("立即更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 下载apk连接地址，downloadUrl
				downloadApk();
			}
		});

		builder.setNegativeButton("稍后再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取消对话框，进入主界面
				enterHome();
				dialog.dismiss();
			}
		});
		// 点击取消的事件监听
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// 点击取消按钮，进入主界面
				enterHome();
				dialog.dismiss();
			}
		});

		builder.show();
	}

	protected void downloadApk() {
		// 先判断SD卡有没有挂载，供APK下载存放的路径。使用Xutils的HttpUtils下载
		// 1.判断SD卡是否可用，是否挂上
		Log.i(tag, "SD卡挂载状态.........." + Environment.getExternalStorageState()
				+ "............." + Environment.MEDIA_MOUNTED);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// 2.获取SD路径
			String path = Environment.getExternalStorageDirectory()
					+ File.separator + "mobilesafe.apk";

			HttpUtils httpUtils = new HttpUtils();
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {

				@Override
				public void onStart() {
					Log.i(tag, "开始下载.......");
					super.onStart();
				}

				// 下载过程中的方法(下载apk的总大小，当前下载存放的位置，是否正在下载)
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.i(tag, "下载中............");
					Log.i(tag, "total = " + total);
					Log.i(tag, "current = " + current);

					super.onLoading(total, current, isUploading);
				}

				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// 下载成功(下载过后的放置在SD卡中的apk)
					Log.i(tag, "下载成功");
					File file = responseInfo.result;
					ToastUtil.show(getApplicationContext(), "下载存放在:"
							+ responseInfo.result.getAbsolutePath());
					// 提示用户安装
					installApk(file);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.i(tag, "下载失败");
					ToastUtil.show(getApplicationContext(), "下载失败");
					enterHome();
					// 下载失败

				}

			});
		}
	}

	/**
	 * 安装对应apk
	 * 
	 * @param file
	 *            安装文件
	 */
	protected void installApk(File file) {
		// 系统应用界面,源码,安装apk入口
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		/*
		 * // 文件作为数据源 intent.setData(Uri.fromFile(file)); // 设置安装的类型
		 * intent.setType("application/vnd.android.package-archive");
		 */
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		// startActivity(intent);
		startActivityForResult(intent, 1);
		/*
		 * <intent-filter> <action android:name="android.intent.action.VIEW" />
		 * <category android:name="android.intent.category.DEFAULT" /> <data
		 * android:scheme="content" /> <data android:scheme="file" /> <data
		 * android:mimeType="application/vnd.android.package-archive" />
		 * </intent-filter>
		 */

	}

	// 开启一个Activity后，返回结果的方法
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// 在开启一个新的界面后，将导航界面关闭，进入主界面
		finish();
		// startActivityForResult(intent, 0);

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

				// Message message = new Message();
				// Message()相对Message.obtain低效
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					// 1.封装url地址
					URL url = new URL(
							"http://192.168.1.108:8080/update_version.json");
					// 2.开启一个链接
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();

					// 3.设置常见参数(请求头)
					// 请求超时
					connection.setConnectTimeout(2000);
					// 读取超时
					connection.setReadTimeout(2000);
					// 请求方式 默认为get
					// connection.setRequestMethod("POST");
					// 4.获取响应码
					if (connection.getResponseCode() == 200) {
						// 5.以流的形式，装数据获取下来
						InputStream is = connection.getInputStream();
						// 6.将流转换成字符串
						String jsonString = StreamUtil.stream2String(is);
						// 7.解释json数据
						JSONObject jsonObject = new JSONObject(jsonString);
						String versionName = jsonObject
								.getString("versionName");
						mVersionDes = jsonObject.getString("versionDes");
						String versionCode = jsonObject
								.getString("versionCode");
						mDownloadUrl = jsonObject.getString("downloadUrl");
						// debug
						Log.i(tag, versionName);
						Log.i(tag, mVersionDes);
						Log.i(tag, versionCode);
						Log.i(tag, mDownloadUrl);
						// 8.比对版本号(本地版本号<服务器版本号，提示用户更新,否则进入程序主界面)
						if (mLocalVersionCode < Integer.parseInt(versionCode)) {
							// 提示用户更新,弹出对话框(UI),子线程不能更新UI,要用消息机制(需要创建Handler和msg对象)
							msg.what = UPDATE_VERSION;
						} else {
							msg.what = ENTER_HOME;
							// 进入应用程序界面
						}

					}

				} catch (MalformedURLException e) {
					msg.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					msg.what = IO_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = JSON_ERROR;
					e.printStackTrace();
				} finally {// finally里的代码一定执行
					// 指定睡眠时间，请求网络的时长超过4秒则不做处理
					// 小于4秒，强制让其睡眠满4秒种
					// Thread.sleep(4000); 不精准
					long endTime = System.currentTimeMillis();
					if (endTime - startTime < 4000) {
						try {
							Thread.sleep(4000 - (endTime - startTime));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
				}

			};

		}.start();

		/*
		 * 第二种方法通过接口方式写线程 new Thread(new Runnable() { public void run() { }
		 * }){}.start();
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
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化UI方法 alt+shift+j
	 */
	private void initUI() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);

	}

}
