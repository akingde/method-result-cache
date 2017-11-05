/**
 * 
 * @author swing
 */
package org.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 检验你的实现是否正确： MD5 ("") = d41d8cd98f00b204e9800998ecf8427e MD5 ("a") =
 * 0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") =
 * 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") =
 * f96b697d7cb7938d525a2f31aaf161d0 MD5 ("abcdefghijklmnopqrstuvwxyz") =
 * c3fcd3d76192e4007dfb496cca67e13b
 */

public class MD5 {
	private static Log log = LogFactory.getLog(MD5.class);
	private static final String ALGORITHM = "MD5";

	/**
	 * 字符串加密方法。传入一个字符串，返回经过MD5加密后的一个字符串
	 * 
	 * @param strInput
	 * @return
	 */
	
	public static String encryptHex(String strInput) {
		return encryptHex(strInput, "UTF-8");
	}

	public static String encryptHex(String strInput, String charset) {
		byte b[] = null;
		try {
			b = strInput.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			b = strInput.getBytes();
		}
		return encryptHex(b);
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

}
