package com.whq.mobilesafe.receiver;

import java.net.ContentHandler;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.activity.ConstantValue;
import com.whq.mobilesafe.service.LocationService;
import com.whq.mobilesafe.utils.SpUtil;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

	private static final String tag = "SmsReceiver";
	private DevicePolicyManager mDPM;
	private ComponentName mDeiceAdminSample;

	public void onReceive(Context context, Intent intent) {
		Log.i(tag, "接收到短信----------------------------");
		// 获取设备管理者对象
		mDPM = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeiceAdminSample = new ComponentName(context, DeviceAdmin.class);
		boolean open_security = SpUtil.getBoolean(context,
				ConstantValue.OPEN_SECURITY, false);
		// 1.判断是否开启了安全保护
		if (open_security) {
			// 2.获取短信内容
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			// 3.遍历循环 变量类型 变量名称：对象
			for (Object object : objects) {
				// 4.获取短信对象
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
				// 5.获取短信基本信息
				String originatingAddress = sms.getOriginatingAddress();
				String messageBody = sms.getMessageBody();
				// 6.判断接收短信是否含有播放音乐内容
				if (messageBody.contains("#*alarm*#")) {
					// 7.播放音乐
					MediaPlayer mediaPlayer = MediaPlayer.create(context,
							R.raw.ylzs);
					mediaPlayer.setLooping(true);
					mediaPlayer.start();
				}

				if (messageBody.contains("#*location*#")) {
					// 8.开启获取位置服务
					context.startService(new Intent(context,
							LocationService.class));
				}
				if (messageBody.contains("#*lockscrenn*#")) {
					// 9.一键锁屏
					if (mDPM.isAdminActive(mDeiceAdminSample)) {
						mDPM.lockNow();
					}
				}
				if (messageBody.contains("#*wipedate*#")) {
					// 10.抹除数据
					if (mDPM.isAdminActive(mDeiceAdminSample)) {
						mDPM.wipeData(0);// 手机数据
						// mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//外部存储卡
					}
				}
			}

		}
	}
}
