package com.whq.mobilesafe.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static String encoder(String psd) {
		try {
			//加盐
			psd = psd + "mobilesafe";
			// 1.加密算法
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// 2.将要加密的字符转成byte类型数组，然后进行随机哈希过程
			byte[] bs = digest.digest(psd.getBytes());
			// 3.循环遍历bs,然后让其生成32位字符串，固定写法
			// 4.拼接字符串
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bs) {
				int i = b & 0xff;
				// int类型的i需要转换成16进制字符
				String hexString = Integer.toHexString(i);
				if (hexString.length() < 2) {
					hexString = "0" + hexString;
				}
				// System.out.print(hexString);
				stringBuffer.append(hexString);
			}
			return stringBuffer.toString();
			// 打印测试
			//System.out.println(stringBuffer.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";

	}

}
