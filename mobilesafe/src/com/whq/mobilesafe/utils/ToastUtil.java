package com.whq.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	// ��ӡ��˾
	/**
	 * @param ctx �����Ļ���
	 * @param txt ��ӡ�ı�����
	 */
	public static void show(Context ctx, String txt) {

		Toast.makeText(ctx, txt, 0).show();
	}

}
