package com.doservlet.framework.util;

import java.io.File;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 基础加密组件
 * 
 * @author 陈小默
 * @version 1.1
 */
public abstract class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 * 
	 * @since 1.0
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 *            简单密文
	 * @return 明文
	 * @throws Exception
	 * @since 1.0
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key.getBytes());
	}

	/**
	 * Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。 （The Base64
	 * Content-Transfer-Encoding is designed to represent arbitrary sequences of
	 * octets in a form that need not be humanly readable.） 常用于邮箱以及HTTP加密
	 * 
	 * @param key
	 *            明文
	 * @return 简单密文
	 * @throws Exception
	 * @since 1.0
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return new String(Base64.encodeBase64(key));
	}

	/**
	 * MD5 -- message-digest algorithm 5 （信息-摘要算法）。
	 * 广泛使用的加密和文件校验技术。任意文件，经过MD5后都能生成唯一的MD5值。
	 * 
	 * @param data
	 *            数据
	 * @return MD5码
	 * @throws Exception
	 * @since 1.0
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();
	}

	/**
	 * MD5 -- message-digest algorithm 5 （信息-摘要算法）。
	 * 广泛使用的加密和文件校验技术。任意文件，经过MD5后都能生成唯一的MD5值。
	 * 
	 * @param file
	 *            文件
	 * @return MD5码
	 * @throws Exception
	 * @since 1.1
	 */
	public static byte[] encryptMD5(File file) throws Exception {
		return encryptMD5(new TextFileUtils(file).getBytes());
	}

	/**
	 * SHA(Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。
	 * SHA是公认的安全加密算法，较之MD5更为安全。
	 * 
	 * @param data
	 *            数据
	 * @return SHA校验码
	 * @throws Exception
	 * @since 1.0
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	/**
	 * SHA(Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。
	 * SHA是公认的安全加密算法，较之MD5更为安全。
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(File file) throws Exception {
		return encryptSHA(new TextFileUtils(file).getBytes());
	}

	/**
	 * 初始化HMAC密钥。 HMAC(Hash Message Authentication
	 * Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
	 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
	 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。 接收方利用与发送方共享的密钥进行鉴别认证
	 * 
	 * @return HMAC密钥
	 * @throws Exception
	 * @since 1.0
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            HMAC密钥
	 * @return MAC数据块（接收方根据明文文件以及发送方的HMAC密钥生成一个MAC数据块，再根据发送方发送的MAC数据块进行比对）
	 * @throws Exception
	 * @since 1.0
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}

	/**
	 * 获取指定长度的随机字符串,字符串取值范围0-9a-zA-Z共62个取值
	 * 
	 * @param lenth
	 *            需要的字符串长度
	 * @return 随机生成的字符串
	 * @since 1.1
	 */
	public static String getRandomString(int lenth) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < lenth; i++) {
			char c = '0';
			int ran = (int) (Math.random() * 62);
			if (ran < 10)
				c = (char) ('0' + ran);
			else if (ran >= 10 && ran < 36)
				c = (char) ('a' + ran - 10);
			else if (ran >= 36)
				c = (char) ('A' + ran - 36);
			buffer.append(c);
		}
		return buffer.toString();
	}

	/**
	 * 获取指定长度的随机数字字符串，取值范围0-9
	 * 
	 * @param lenth
	 *            待生成的字符串长度
	 * @return 随机数字字符串
	 * @since 1.1
	 */
	public static String getRandomNum(int lenth) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < lenth; i++) {
			char c = '0';
			int ran = (int) (Math.random() * 10);
			c = (char) ('0' + ran);
			buffer.append(c);
		}
		return buffer.toString();
	}
	// public static String
}