package com.whq.mobilesafe.receiver;

import java.net.ContentHandler;

import com.whq.mobilesafe.activity.ConstantValue;
import com.whq.mobilesafe.utils.SpUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	private static final String tag = "BootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(tag, "---------------�����ֻ����ù㲥------------------------");
		//1.��ȡԭ��sim����
		String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER, null);
		//2.��ȡ������sim����
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = tm.getSimSerialNumber()+"test";
		//3.�ж�����sim���룬��һ�����ͱ�������
		if (!sim_number.equals(simSerialNumber)) {
			String phone_num = SpUtil.getString(context, ConstantValue.PHONE_NUM, null);
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("5556", null, "sim change!!!-----------------------------", null, null);
		}
	}

}
