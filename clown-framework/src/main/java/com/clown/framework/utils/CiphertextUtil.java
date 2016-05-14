package com.clown.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
/**
 *  加密
 * @author colin.tan
 *
 */
public class CiphertextUtil {
	public static final String MD5 = "MD5";
	public static final String SHA_1 = "SHA-1";
	public static final String SHA_256 = "SHA-256";
	private static final char[] CH_HEX = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 获取系统流水号
	 * 
	 * @param length
	 *            指定流水号长度
	 * @param toNumber
	 *            指定流水号是否全由数字组成
	 */
	public static String getSysJournalNo(int length, Boolean isNumber) {
		// replaceAll()之后返回的是一个由十六进制形式组成的且长度为32的字符串
		String uuid = UUID.randomUUID().toString().replaceAll("-", ".");
		if (uuid.length() > length) {
			uuid = uuid.substring(0, 20);
		} else if (uuid.length() < length) {
			for (int i = 0; i < length - uuid.length(); i++) {
				uuid = uuid + Math.round(Math.random() * 9);
			}
		}
		if (isNumber) {
			return uuid.replaceAll("a", "1").replaceAll("b", "2")
					.replaceAll("c", "3").replaceAll("d", "4")
					.replaceAll("e", "5").replaceAll("f", "6");
		} else {
			return uuid;
		}
	}

	/**
	 * 加密字符串
	 * 
	 * @param sourceStr
	 *            需要加密目标字符串
	 * @param algorithmsName
	 *            算法名称(如:MD5,SHA-1,SHA-256)
	 * @return
	 */
	public static String passAlgorithmsCiphering(String sourceStr,
			String algorithmsName) {
		String password = "";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(algorithmsName);
			// 使用指定byte[]更新摘要
			md.update(sourceStr.getBytes());
			// 完成计算，返回结果数组
			byte[] b = md.digest();
			password = byteArrayToHex(b);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

	/**
	 * 将字节数组转为十六进制字符串
	 *
	 * @param bytes
	 * @return 返回16进制字符串
	 */
	private static String byteArrayToHex(byte[] bytes) {
		// 一个字节占8位，一个十六进制字符占4位；十六进制字符数组的长度为字节数组长度的两倍
		char[] chars = new char[bytes.length * 2];
		int index = 0;
		for (byte b : bytes) {
			// 取字节的高4位
			chars[index++] = CH_HEX[b >>> 4 & 0xf];
			// 取字节的低4位
			chars[index++] = CH_HEX[b & 0xf];
		}
		return new String(chars);
	}
}
