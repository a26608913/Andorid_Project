package com.example.devicemanager;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ComponentName mDeviceAdminSample;
	private DevicePolicyManager mDPM;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button bt_start = (Button) findViewById(R.id.bt_start);
		Button bt_lock = (Button) findViewById(R.id.bt_lock);
		Button bt_wipedata = (Button) findViewById(R.id.bt_wipedata);
		Button bt_uninstall = (Button) findViewById(R.id.bt_uninstall);
		mDeviceAdminSample = new ComponentName(this, DeviceAdmin.class);
		mDPM = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		
		bt_start.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				//开启设备管理器的activity
				Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
				intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample);
				intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"设备管理器");
				startActivity(intent);
			}
		});
	
		bt_lock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mDPM.isAdminActive(mDeviceAdminSample)) {
					//锁屏
					mDPM.lockNow();
					//设置密码同时去设置密码
					mDPM.resetPassword(null, 0);
					
				}else {
					Toast.makeText(getApplicationContext(), "请先激活", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		bt_wipedata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mDPM.isAdminActive(mDeviceAdminSample)) {
					mDPM.wipeData(0);//手机数据
					//mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//外部存储数据
				}else {
					Toast.makeText(getApplicationContext(), "请先激活", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		bt_uninstall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	/*		        <activity android:name=".UninstallerActivity"
			                android:configChanges="orientation|keyboardHidden"
			                android:theme="@style/Theme.Transparent">
			            <intent-filter>
			                <action android:name="android.intent.action.VIEW" />
			                <action android:name="android.intent.action.DELETE" />---------
			                <category android:name="android.intent.category.DEFAULT" />---------
			                <data android:scheme="package" />----------
			            </intent-filter>
			        </activity>*/
					Intent intent = new Intent("android.intent.action.DELETE");
					intent.addCategory("android.intent.category.DEFAULT");
					intent.setData(Uri.parse("package:"+getPackageName()));
					startActivity(intent);
			}
		});
	}
	
		
}
