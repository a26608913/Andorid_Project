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
		Log.i(tag, "---------------重启手机后获得广播------------------------");
		//1.获取原来sim号码
		String sim_number = SpUtil.getString(context, ConstantValue.SIM_NUMBER, null);
		//2.获取重启后sim号码
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String simSerialNumber = tm.getSimSerialNumber()+"test";
		//3.判断两个sim号码，不一样发送报警短信
		if (!sim_number.equals(simSerialNumber)) {
			String phone_num = SpUtil.getString(context, ConstantValue.PHONE_NUM, null);
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage("5556", null, "sim change!!!-----------------------------", null, null);
		}
	}

}
