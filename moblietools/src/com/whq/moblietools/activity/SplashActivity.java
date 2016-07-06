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
		// ��Ҫ���Զ��巽��������Ҫ��handlerMessage()��������Ч��
		public void handleMessage(android.os.Message msg) {
			// �յ�״̬�����ж�
			switch (msg.what) {
			case UPDATE_VERSION:
				// ��ʾ�����Ի���
				showUpdateDialog();
				break;
			case ENTER_HOME:
				enter_home();
				finish();
				break;
			case URL_ERROR:
				ToastUtil.show(getApplicationContext(), "url�쳣");
				enter_home();
				break;
			case IO_ERROR:
				ToastUtil.show(getApplicationContext(), "�����ȡ�쳣");
				enter_home();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json�쳣");
				enter_home();
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_main);
		// 1.�ҵ���ʾ�汾�ؼ�
		TextView tv_version_number = (TextView) findViewById(R.id.tv_version_number);
		tv_version_number.setText("�汾����:" + showVersionName());

		// 2.��ʾ���ݰ汾�źͰ汾����
		showData();
		// ����°汾����
		checkVersionUpdate();
	}

	/**
	 * ��ʾ�����Ի���
	 */
	protected void showUpdateDialog() {
		Builder builder = new AlertDialog.Builder(this);
		// ��ʾͼ��
		builder.setIcon(R.drawable.ic_launcher);
		// ��ʾ����
		builder.setTitle("���°汾");
		// ��ʾ������Ϣ
		builder.setMessage(mVersionDes);
		// ��ʾ���¡��Ժ��߼�
		builder.setPositiveButton("����", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// �����滻����
				downloadApk();
			}
		});

		builder.setNegativeButton("�Ժ�", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ȡ������,����������
				enter_home();
			}
		});

		builder.setOnCancelListener(new OnCancelListener() {
			// ���ȡ����ť������������
			public void onCancel(DialogInterface dialog) {
				enter_home();
				dialog.dismiss();
			}
		});
		builder.show();

	}

	/**
	 * ���°�װapk
	 */
	protected void downloadApk() {
		// 1.�ж�sd���Ƿ����
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// 2.ʹ��XUtils�������
			HttpUtils httpUtils = new HttpUtils();
			// ��ȡsd��·��
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "mobiletool.apk";
			Log.i(tag, "======================="+path+"============="+mDownloadUrl+"===========");
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {

				// ׼����ʼ����
				public void onStart() {

					Log.i(tag, "׼����ʼ����......");
					super.onStart();
				}

				// ������
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.i(tag, "total:" + total);
					Log.i(tag, "������..................");
					Log.i(tag, "current:" + current);
					super.onLoading(total, current, isUploading);
				}

				// ��ʼ�ɹ�
				public void onSuccess(ResponseInfo<File> responseInfo) {
					File file = responseInfo.result;
					ToastUtil.show(getApplicationContext(), "�Ѿ����ص�"
							+ responseInfo.result.getAbsolutePath());
					//��ʾ�û���װ
					installApk(file);
				}

				// ����ʧ��
				public void onFailure(HttpException arg0, String arg1) {
					Log.i(tag, "����ʧ��");
				}

			});
		}
	}
	
	/**
	 * ��װ��Ӧapk
	 * ʹ��ϵͳ���
	 * @param file ���صİ�װ�ļ�
	 */
	
	/*
	 *  <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:scheme="content" />
                <data android:scheme="file" />
                <data android:mimeType="application/vnd.android.package-archive" />
	 */
	protected void installApk(File file) {
		//ϵͳӦ�ý��棬Դ��
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		startActivityForResult(intent, 0);
	}
	//����һ��Activity�󣬷��ؽ�����÷���
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enter_home();
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void enter_home() {
		// �������������
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		//�ڿ���һ���½���󣬽���������ر�(��������ֻ�ܼ�һ��)
		finish();
	}

	/**
	 * �����°汾���� �ӷ�������ȡjson�汾��
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
						// �����ķ�ʽ��ȡ����
						InputStream is = connection.getInputStream();
						// ����ת�����ַ���
						String jsonString = Utils.Strem2String(is);
						// ����json����
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
						// �ж�����������汾�ȱ��ذ汾��,������������,ֱ�ӽ���������
						if (mLocalVersionCode < Integer.parseInt(versionCode)) {
							// ����Ҫ�����Ի������̲߳��ܸ���UI,Ҫ����Ϣ����Handler��msg����
							msg.what = UPDATE_VERSION;
						} else {
							// ����������
							msg.what = ENTER_HOME;
						}
					}
				} catch (MalformedURLException e) {
					// url�쳣
					msg.what = URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					// �����ȡ�쳣
					msg.what = IO_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					// json�쳣
					msg.what = JSON_ERROR;
					e.printStackTrace();
				} finally {
					long endTime = System.currentTimeMillis();
					// ǿ��ͣ��4��
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
	 * ��ʾ���ݰ汾���ƺͰ汾��
	 */
	private void showData() {
		showVersionName();
		mLocalVersionCode = showVersionCode();
	}

	/**
	 * @return ���ذ汾�� ���ط�0Ϊ����
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
	 * @return ���ذ������ַ� ����ǿ�Ϊ����
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
