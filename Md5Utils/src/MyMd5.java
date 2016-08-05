import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.xml.internal.fastinfoset.Encoder;

public class MyMd5 {

	/**
	 * @param args
	 *            MD5
	 */
	public static void main(String[] args) {
		String psd = "123" + "moblie";
		encoder(psd);
	}

	private static void encoder(String psd) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bs = digest.digest(psd.getBytes());

			for (byte b : bs) {
				int i = b & 0xff;
				String hexString = Integer.toHexString(i);
				if (hexString.length() < 2) {
					hexString = "0" + hexString;
				}

				stringBuffer.append(hexString);
			}
			System.out.println(stringBuffer);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

}
