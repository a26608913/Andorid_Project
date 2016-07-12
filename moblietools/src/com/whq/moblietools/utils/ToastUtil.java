package com.whq.moblietools.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void show(Context ctx, String txt) {
		Toast.makeText(ctx, txt, 0).show();
	}

}
