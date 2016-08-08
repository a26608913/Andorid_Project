package com.whq.mobilesafe.receiver;

import com.whq.mobilesafe.R;
import com.whq.mobilesafe.activity.ConstantValue;
import com.whq.mobilesafe.utils.SpUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

	private static final String tag = "SmsReceiver";

	public void onReceive(Context context, Intent intent) {
		Log.i(tag, "���յ�����----------------------------");
		boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);
		//1.�ж��Ƿ����˰�ȫ����
		if (open_security) {
			//2.��ȡ��������
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			//3.����ѭ��		��������    �������ƣ�����
			for (Object object : objects) {
				//4.��ȡ���Ŷ���
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
				//5.��ȡ���Ż�����Ϣ
				String originatingAddress = sms.getOriginatingAddress();
				String messageBody = sms.getMessageBody();
				//6.�жϽ��ն����Ƿ��в�����������
				if (messageBody.contains("#*alarm*#")) {
					//7.��������
					MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
					mediaPlayer.setLooping(true);
					mediaPlayer.start();
				}
			}
		
		}
	}

}
