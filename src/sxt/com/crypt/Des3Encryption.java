package sxt.com.crypt;
import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密工具类
 */
public abstract class Des3Encryption {
	/**
	 * 编码类型
	 */
	public static final String CHAR_ENCODING = "UTF-8";

	/**
	 * 进行加密操作
	 * @param key 加密密匙
	 * @param data 待加密数据
	 * @return
	 * @throws Exception 各种异常
	 */
	public static byte[] encode(byte[] key, byte[] data) throws Exception {
		return MessageAuthenticationCode.des3Encryption(key, data);
	}

	/**
	 * 解密方法
	 * @param key 解密密匙
	 * @param value 待解密二进制数组
	 * @return
	 * @throws Exception 各种异常
	 */
	public static byte[] decode(byte[] key, byte[] value) throws Exception {
		return MessageAuthenticationCode.des3Decryption(key, value);
	}

	/**
	 * 通过字符串加密
	 * @param key 加密密匙字符
	 * @param data 待加密字符串
	 * @return
	 */
	public static String encode(String key, String data) {
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] dataByte = data.getBytes(CHAR_ENCODING);
			byte[] valueByte = MessageAuthenticationCode.des3Encryption(keyByte, dataByte);
			String value = new String(Base64.encode(valueByte), CHAR_ENCODING);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密字符串
	 * @param key 解密密匙
	 * @param value 待解密字符串
	 * @return
	 */
	public static String decode(String key, String value) {
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] valueByte = Base64.decode(value.getBytes(CHAR_ENCODING));
			byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte, valueByte);
			String data = new String(dataByte, CHAR_ENCODING);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过HEX加密
	 * @param key 加密密匙
	 * @param data 待加密数据
	 * @return
	 */
	public static String encryptToHex(String key, String data) {
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] dataByte = data.getBytes(CHAR_ENCODING);
			byte[] valueByte = MessageAuthenticationCode.des3Encryption(keyByte, dataByte);
			String value = ConvertUtils.toHex(valueByte);
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 从HEX解密
	 * @param key 解密密匙
	 * @param value 待解密数据
	 * @return
	 */
	public static String decryptFromHex(String key, String value) {
		try {
			byte[] keyByte = key.getBytes(CHAR_ENCODING);
			byte[] valueByte = ConvertUtils.fromHex(value);
			byte[] dataByte = MessageAuthenticationCode.des3Decryption(keyByte, valueByte);
			String data = new String(dataByte, CHAR_ENCODING);
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UDP加密
	 * @param key 加密密匙
	 * @param data 待加密数据
	 * @return
	 */
	public static String udpEncrypt(String key, String data) {
		try {
			Key k = updGenerateKey(key);
			IvParameterSpec iVSpec = new IvParameterSpec(new byte[8]);
			// ����
			Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			c.init(1, k, ((java.security.spec.AlgorithmParameterSpec) (iVSpec)));
			byte[] output = c.doFinal(data.getBytes("UTF-8"));
			return new String(Base64.encode(output), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * udp密钥生成器
	 * @param key 解密密匙
	 */
	public static Key updGenerateKey(String key) {
		try {
			DESedeKeySpec keySpec = new DESedeKeySpec(udpHexDecode(key));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			// �����Կ
			Key k = ((Key) (keyFactory.generateSecret(((java.security.spec.KeySpec) (keySpec)))));
			return k;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UDP解密
	 * @param key 解密密匙
	 * @param data 待解密数据
	 */
	public static String udpDecrypt(String key, String data) {
		try {
			byte[] input = Base64.decode(data.getBytes("UTF-8"));
			Key k = updGenerateKey(key);
			IvParameterSpec iVSpec = new IvParameterSpec(new byte[8]);
			Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			c.init(2, k, ((java.security.spec.AlgorithmParameterSpec) (iVSpec)));
			byte[] output = c.doFinal(input);
			return new String(output, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UDPHEX解码
	 * @param s 待解密字符串
	 */
	public static byte[] udpHexDecode(String s) {
		byte[] abyte0 = new byte[s.length() / 2];
		String s1 = s.toLowerCase();
		for (int i = 0; i < s1.length(); i += 2) {
			char c = s1.charAt(i);
			char c1 = s1.charAt(i + 1);
			int j = i / 2;
			if (c < 'a') {
	            abyte0[j] = (byte) (c - 48 << 4);
            } else {
	            abyte0[j] = (byte) ((c - 97) + 10 << 4);
            }
			if (c1 < 'a') {
	            abyte0[j] += (byte) (c1 - 48);
            } else {
	            abyte0[j] += (byte) ((c1 - 97) + 10);
            }
		}
		return abyte0;
	}

	/**
	 * 加密字符
	 * @param value 待加密数据
	 */
	public static String encode(String value) {
		return encode("a1b2c3d4e5f6g7h8i9j0klmn", value);
	}

	/**
	 * 解密字符
	 * @param value 待解密数据
	 * @return
	 */
	public static String decode(String value) {
		return decode("a1b2c3d4e5f6g7h8i9j0klmn", value);
	}

	/**
	 * Main方法
	 * @param args 键盘输入参数
	 * @throws UnsupportedEncodingException 各种异常
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = "1352775250689&email=heavengrass_1991@163.com";
		String key = "Rk6SokaffBChWCw2ZBiDrUZD";
		String crypt = encode(key, content);
		System.out.println(crypt);
	}
}