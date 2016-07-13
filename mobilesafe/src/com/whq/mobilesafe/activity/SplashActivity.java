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
	 * �����°汾��״̬��
	 */
	protected static final int UPDATE_VERSION = 100;
	/**
	 * �����������״̬��
	 */
	protected static final int ENTER_HOME = 101;
	/**
	 * URL�쳣״̬��
	 */
	protected static final int URL_ERROR = 102;
	/**
	 * IO�쳣״̬��
	 */
	protected static final int IO_ERROR = 103;
	/**
	 * JSON�쳣״̬��
	 */
	protected static final int JSON_ERROR = 104;

	private TextView tv_version_name;
	private int mLocalVersionCode;
	private String mVersionDes;
	private String mDownloadUrl;
	// ��Ա������Ҫm��ͷ�����ֺ�Դ��һ�´�
	private Handler mHandler = new Handler() {
		// ��Ҫ�Զ��巽��������Ҫ��handleMessage()����Ч��
		public void handleMessage(Message msg) {
			// �յ�״̬�����ж�
			switch (msg.what) {
			case UPDATE_VERSION:
				// �����Ի�����ʾ�û�����
				showUpdateDialog();
				break;
			case ENTER_HOME:
				// ����������
				enterHome();
				break;
			case URL_ERROR:
				// ��Щ�쳣�Ǵ�����Լ���������ʱ�ǵ�ɾ��
				ToastUtil.show(getApplicationContext(), "url�쳣");
				enterHome();
				break;
			case IO_ERROR:
				ToastUtil.show(getApplicationContext(), "io��ȡ�쳣");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json�����쳣");
				enterHome();
				break;
			}

		};

	};
	private RelativeLayout rl_root;

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
		// ��ʼ������
		initAnimation();
	}

	/**
	 * ���õ���Ķ���Ч��
	 */
	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(1000 * 5);
		rl_root.startAnimation(alphaAnimation);
	}

	/**
	 * �����Ի�����ʾ�û�����
	 */
	protected void showUpdateDialog() {
		// �Ի�����������Activity���ڵ�,������getApplicationContext()
		Builder builder = new AlertDialog.Builder(this);
		// �������Ͻ�ͼ��
		builder.setIcon(R.drawable.ic_launcher);
		// ���ñ���
		builder.setTitle("�汾����");
		// ���������ڴ�
		builder.setMessage(mVersionDes);
		// ������������������
		builder.setPositiveButton("��������", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ����apk���ӵ�ַ��downloadUrl
				downloadApk();
			}
		});

		builder.setNegativeButton("�Ժ���˵", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// ȡ���Ի��򣬽���������
				enterHome();
				dialog.dismiss();
			}
		});
		// ���ȡ�����¼�����
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// ���ȡ����ť������������
				enterHome();
				dialog.dismiss();
			}
		});

		builder.show();
	}

	protected void downloadApk() {
		// ���ж�SD����û�й��أ���APK���ش�ŵ�·����ʹ��Xutils��HttpUtils����
		// 1.�ж�SD���Ƿ���ã��Ƿ����
		Log.i(tag, "SD������״̬.........." + Environment.getExternalStorageState()
				+ "............." + Environment.MEDIA_MOUNTED);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// 2.��ȡSD·��
			String path = Environment.getExternalStorageDirectory()
					+ File.separator + "mobilesafe.apk";

			HttpUtils httpUtils = new HttpUtils();
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {

				@Override
				public void onStart() {
					Log.i(tag, "��ʼ����.......");
					super.onStart();
				}

				// ���ع����еķ���(����apk���ܴ�С����ǰ���ش�ŵ�λ�ã��Ƿ���������)
				public void onLoading(long total, long current,
						boolean isUploading) {
					Log.i(tag, "������............");
					Log.i(tag, "total = " + total);
					Log.i(tag, "current = " + current);

					super.onLoading(total, current, isUploading);
				}

				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// ���سɹ�(���ع���ķ�����SD���е�apk)
					Log.i(tag, "���سɹ�");
					File file = responseInfo.result;
					ToastUtil.show(getApplicationContext(), "���ش����:"
							+ responseInfo.result.getAbsolutePath());
					// ��ʾ�û���װ
					installApk(file);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.i(tag, "����ʧ��");
					ToastUtil.show(getApplicationContext(), "����ʧ��");
					enterHome();
					// ����ʧ��

				}

			});
		}
	}

	/**
	 * ��װ��Ӧapk
	 * 
	 * @param file
	 *            ��װ�ļ�
	 */
	protected void installApk(File file) {
		// ϵͳӦ�ý���,Դ��,��װapk���
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		/*
		 * // �ļ���Ϊ����Դ intent.setData(Uri.fromFile(file)); // ���ð�װ������
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

	// ����һ��Activity�󣬷��ؽ���ķ���
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		// �ڿ���һ���µĽ���󣬽���������رգ�����������
		finish();
		// startActivityForResult(intent, 0);

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

				// Message message = new Message();
				// Message()���Message.obtain��Ч
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					// 1.��װurl��ַ
					URL url = new URL(
							"http://192.168.1.108:8080/update_version.json");
					// 2.����һ������
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();

					// 3.���ó�������(����ͷ)
					// ����ʱ
					connection.setConnectTimeout(2000);
					// ��ȡ��ʱ
					connection.setReadTimeout(2000);
					// ����ʽ Ĭ��Ϊget
					// connection.setRequestMethod("POST");
					// 4.��ȡ��Ӧ��
					if (connection.getResponseCode() == 200) {
						// 5.��������ʽ��װ���ݻ�ȡ����
						InputStream is = connection.getInputStream();
						// 6.����ת�����ַ���
						String jsonString = StreamUtil.stream2String(is);
						// 7.����json����
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
						// 8.�ȶ԰汾��(���ذ汾��<�������汾�ţ���ʾ�û�����,����������������)
						if (mLocalVersionCode < Integer.parseInt(versionCode)) {
							// ��ʾ�û�����,�����Ի���(UI),���̲߳��ܸ���UI,Ҫ����Ϣ����(��Ҫ����Handler��msg����)
							msg.what = UPDATE_VERSION;
						} else {
							msg.what = ENTER_HOME;
							// ����Ӧ�ó������
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
				} finally {// finally��Ĵ���һ��ִ��
					// ָ��˯��ʱ�䣬���������ʱ������4����������
					// С��4�룬ǿ������˯����4����
					// Thread.sleep(4000); ����׼
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
		 * �ڶ��ַ���ͨ���ӿڷ�ʽд�߳� new Thread(new Runnable() { public void run() { }
		 * }){}.start();
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
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ʼ��UI���� alt+shift+j
	 */
	private void initUI() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);

	}

}
