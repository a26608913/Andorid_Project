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
		Log.i(tag, "���յ�����----------------------------");
		// ��ȡ�豸�����߶���
		mDPM = (DevicePolicyManager) context
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		mDeiceAdminSample = new ComponentName(context, DeviceAdmin.class);
		boolean open_security = SpUtil.getBoolean(context,
				ConstantValue.OPEN_SECURITY, false);
		// 1.�ж��Ƿ����˰�ȫ����
		if (open_security) {
			// 2.��ȡ��������
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			// 3.����ѭ�� �������� �������ƣ�����
			for (Object object : objects) {
				// 4.��ȡ���Ŷ���
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
				// 5.��ȡ���Ż�����Ϣ
				String originatingAddress = sms.getOriginatingAddress();
				String messageBody = sms.getMessageBody();
				// 6.�жϽ��ն����Ƿ��в�����������
				if (messageBody.contains("#*alarm*#")) {
					// 7.��������
					MediaPlayer mediaPlayer = MediaPlayer.create(context,
							R.raw.ylzs);
					mediaPlayer.setLooping(true);
					mediaPlayer.start();
				}

				if (messageBody.contains("#*location*#")) {
					// 8.������ȡλ�÷���
					context.startService(new Intent(context,
							LocationService.class));
				}
				if (messageBody.contains("#*lockscrenn*#")) {
					// 9.һ������
					if (mDPM.isAdminActive(mDeiceAdminSample)) {
						mDPM.lockNow();
					}
				}
				if (messageBody.contains("#*wipedate*#")) {
					// 10.Ĩ������
					if (mDPM.isAdminActive(mDeiceAdminSample)) {
						mDPM.wipeData(0);// �ֻ�����
						// mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//�ⲿ�洢��
					}
				}
			}

		}
	}
}
