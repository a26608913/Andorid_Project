package com.whq.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	// 打印吐司
	/**
	 * @param ctx 上下文环境
	 * @param txt 打印文本内容
	 */
	public static void show(Context ctx, String txt) {

		Toast.makeText(ctx, txt, 0).show();
	}

}
