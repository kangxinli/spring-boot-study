package com.sample.crypto.utils;

import java.math.BigInteger;

/**
 * 进制转换类
 */
public class RadixUtil {
	private RadixUtil() {
	}

	/**
	 * 进制转换
	 * 
	 * @param src       原数字串
	 * @param radixFrom 数字串的进制
	 * @param radixTo   要转换的进制
	 * @return 转换后的字符串
	 * @see Character#MIN_RADIX
	 * @see Character#MAX_RADIX
	 * @see Integer#toString(int, int)
	 * @see Integer#parseInt(String, int)
	 * @see Integer#valueOf(String, int)
	 */
	public static String radix(String src, int radixFrom, int radixTo) {
		BigInteger bigInteger = new BigInteger(src, radixFrom);
		return bigInteger.toString(radixTo);
	}

	/**
	 * 字节数组转换为16进制字符串, 大写
	 * 
	 * @param b 字节数字
	 * @return 十六进制字符串 小写
	 */
	public static String toHex(byte[] b) {
		return toHexLower(b).toUpperCase();
	}

	/**
	 * 字节数组转换为16进制字符串, 小写
	 * 
	 * @param bs 字节数组
	 * @return 十六进制字符串 小写
	 */
	public static String toHexLower(byte[] bs) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bs) {
			String plainText = Integer.toHexString(0xff & b);
			if (plainText.length() < 2) {
				plainText = "0" + plainText;
			}
			hexString.append(plainText);
		}
		return hexString.toString();
	}

	/**
	 * 16进制字符串转换为字节数组
	 * 
	 * @param hex 十六进制字符串
	 * @return 字节数组
	 */
	public static byte[] toBytes(String hex) {
		byte[] digest = new byte[hex.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = hex.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}
}
