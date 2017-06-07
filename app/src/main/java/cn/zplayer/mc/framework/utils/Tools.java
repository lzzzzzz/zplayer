package cn.zplayer.mc.framework.utils;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {

	/**
	 * 获取32位MD5
	 * 
	 * @param value
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String getMD5(String value) {

		String hashtext = null;
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(value.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			hashtext = hashtext.toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (hashtext == null) {
			return "null";
		}

		return hashtext;
	}

	/**
	 * 判断SD卡 是否存在
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

}
