package com.whq.mobilesafe.utils;

import java.net.ContentHandler;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

	private static SharedPreferences sp;
	//д��������¼��Ŀ��״̬
	/**
	 * @param ctx		�����Ļ���
	 * @param key		�洢�ڵ������
	 * @param value		�洢�ڵ��ֵ
	 */
	public static void putBoolean(Context ctx, String key, boolean value) {
		if (sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	//��
	/**
	 * @param ctx		�����Ļ���
	 * @param key		�洢�ڵ������
	 * @param defValue	��ȡĬ��ֵ �����ߴ˽ڵ��ֵ
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
	//��
	/**
	 * @param ctx		�����Ļ���
	 * @param key		�洢�ڵ������
	 * @param defValue	��ȡĬ��ֵ �����ߴ˽ڵ��ֵ
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
