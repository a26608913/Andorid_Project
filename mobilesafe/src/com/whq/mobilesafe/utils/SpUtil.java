package com.whq.mobilesafe.utils;

import java.net.ContentHandler;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

	private static SharedPreferences sp;
	//写操作，记录条目的状态
	/**
	 * @param ctx		上下文环境
	 * @param key		存储节点的名称
	 * @param value		存储节点的值
	 */
	public static void putBoolean(Context ctx, String key, boolean value) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	//读
	/**
	 * @param ctx		上下文环境
	 * @param key		存储节点的名称
	 * @param defValue	读取默认值 ，或者此节点的值
	 * @return
	 */
	public static boolean getBoolean(Context ctx, String key, boolean defValue){
		
		if(sp == null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	
	public static void putString(Context ctx, String key, String value) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
		}
	//读
	/**
	 * @param ctx		上下文环境
	 * @param key		存储节点的名称
	 * @param defValue	读取默认值 ，或者此节点的值
	 * @return 
	 * @return
	 */
	public static String getString(Context ctx, String key, String defValue){
		
		if(sp == null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}
	
	public static void removeString(Context ctx, String key){
		if(sp == null){
			sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
		}
		sp.edit().remove(key).commit();
	}

}
