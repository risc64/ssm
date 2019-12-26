package com.llf.ssm.util;

import java.util.regex.Pattern;

/**
 * Base64 工具类
 */
public final class Base64 {
	/**
	 * Encodes hex octects into Base64
	 *
	 * @param binaryData
	 *            Array containing binaryData
	 * @return Encoded Base64 array
	 */
	public static String encode(byte[] binaryData) {
		return org.apache.commons.codec.binary.Base64.encodeBase64String(binaryData);
	}

	/**
	 * Decodes Base64 data into octects
	 *
	 * @param encoded
	 *            string containing Base64 data
	 * @return Array containind decoded data.
	 */
	public static byte[] decode(String encoded) {
		return org.apache.commons.codec.binary.Base64.decodeBase64(encoded);
	}

	/**
	 * 判断字符创是否为base64编码
	 * @param str
	 * @return
	 */
	public static boolean isBase64(String str) {
		String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
		return Pattern.matches(base64Pattern, str);

	}

}