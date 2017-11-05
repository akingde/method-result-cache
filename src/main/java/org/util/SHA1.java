/*
 * 
 * @author swing
 */
package org.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>SHA(即Secure Hash Algorithm,安全散列算法)是一种常用的数据加密算法,
 * 它由美国国家标准与技术局(NationalInstitute of Standards and Technology)
 * 于1993 年作为联邦信息处理标准公布(即第一代SHA 算法—SHA-0)。在1995 年和2002 年,
 * 其改进版本SHA-1、SHA-2 也分别正式公布(SHA-1 和SHA-2 具有比SHA-0 更高的安全性)。
 * </p>
 * SHA1产生一个20字节的二进制数组
 */

public class SHA1 {
	private static Log log = LogFactory.getLog(SHA1.class);
	private static final String ALGORITHM = "SHA-1";
	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";

	/**
	 * 字符串SHA-1摘要算法。传入一个字符串，返回经过SHA-1加密后的一个字符串
	 *
	 * @param strInput
	 * @return
	 */
	public static String encryptHex(String strInput) {
		return encryptHex(strInput, ENCODING);
	}

	public static String encryptHex(String strInput, String charset) {
		byte b[] = null;
		try {
			b = strInput.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			b = strInput.getBytes();
		}
		return RadixUtil.binToHex(encrypt(b));
	}

	public static String encryptHex(byte[] byteInput) {
		return RadixUtil.binToHex(encrypt(byteInput));
	}

	public static byte[] encrypt(byte[] byteInput) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.update(byteInput);
			return md.digest();
		} catch (NoSuchAlgorithmException nsae) {
			log.error("No such Algorithm in digest");
			return new byte[] {};
		}
	}

	public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
			throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}

}
