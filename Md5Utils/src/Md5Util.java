import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	/**
	 * @param args
	 *            MD5
	 */
	public static void main(String[] args) {
		//����
		String psd = "123" + "mobile";

		encoder(psd);
	}

	private static void encoder(String psd) {
		try {
			// 1.�����㷨
			MessageDigest digest = MessageDigest.getInstance("MD5");
			// 2.��Ҫ���ܵ��ַ�ת��byte�������飬Ȼ����������ϣ����
			byte[] bs = digest.digest(psd.getBytes());
			// 3.ѭ������bs,Ȼ����������32λ�ַ������̶�д��
			// 4.ƴ���ַ���
			StringBuffer stringBuffer = new StringBuffer();
			for (byte b : bs) {
				int i = b & 0xff;
				// int���͵�i��Ҫת����16�����ַ�
				String hexString = Integer.toHexString(i);
				if (hexString.length() < 2) {
					hexString = "0" + hexString;
				}
				// System.out.print(hexString);
				stringBuffer.append(hexString);
			}
			// ��ӡ����
			System.out.println(stringBuffer.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

}
