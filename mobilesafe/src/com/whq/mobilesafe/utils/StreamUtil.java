package com.whq.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	/**
	 * 流转换成字符串对象
	 * 
	 * @param inputStream
	 *            流对象
	 * @return 流转换成字符串对象 返回null代表异常
	 */
	public static String stream2String(InputStream is) {
		// 1.在读取的过程中，将读取的内容存储在缓存中，然后一次性转换成字符串返回
		ByteArrayOutputStream bos = new ByteArrayOutputStream();// ByteArrayOutputStream:功能为临时存储一部份内容，然后一次性返回
		// 2.读流操作，读到没有为止(循环)
		byte[] buffer = new byte[1024];
		// 3.一个维护记录读取内容的临时变量
		int temp = -1;
		try {
			while ((temp = is.read(buffer)) != -1) {
				// 将数据临时写到bos对象中
				bos.write(buffer, 0, temp);
			}
			// 返回数据转换成字符
			return bos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// 先读后写
				is.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

}
