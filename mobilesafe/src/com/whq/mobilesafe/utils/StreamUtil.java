package com.whq.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	/**
	 * ��ת�����ַ�������
	 * 
	 * @param inputStream
	 *            ������
	 * @return ��ת�����ַ������� ����null�����쳣
	 */
	public static String stream2String(InputStream is) {
		// 1.�ڶ�ȡ�Ĺ����У�����ȡ�����ݴ洢�ڻ����У�Ȼ��һ����ת�����ַ�������
		ByteArrayOutputStream bos = new ByteArrayOutputStream();// ByteArrayOutputStream:����Ϊ��ʱ�洢һ�������ݣ�Ȼ��һ���Է���
		// 2.��������������û��Ϊֹ(ѭ��)
		byte[] buffer = new byte[1024];
		// 3.һ��ά����¼��ȡ���ݵ���ʱ����
		int temp = -1;
		try {
			while ((temp = is.read(buffer)) != -1) {
				// ��������ʱд��bos������
				bos.write(buffer, 0, temp);
			}
			// ��������ת�����ַ�
			return bos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				// �ȶ���д
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
