package md5.crypt;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * <p>
 * Title: 加解密功能实现
 */
public class CryptUtilImpl implements CryptUtil {

	/**
	 * 构造器
	 */
	public CryptUtilImpl() {
	}

	/**
	 * 按照Triple-DES算法，对数据进行加密
	 * 
	 * @param source - 明文数据
	 * @param key - 密钥
	 * @return - 密文数据
	 */
	public String cryptDes(String source, String key) {
		// 对称加密
		return Des3Encryption.encode(key, source);
	}

	/**
	 * 按照Triple-DES算法，对数据进行解密
	 * 
	 * @param des - 密文数据
	 * @param key - 密钥
	 * @return - 明文数据
	 */
	public String decryptDes(String des, String key) {
		// 对称解密
		return Des3Encryption.decode(key, des);
	}

	/**
	 * 按照Triple-DES算法，使用系统固定的密钥"a1b2c3d4e5f6g7h8i9j0klmn"，对数据进行加密
	 * 
	 * @param source - 明文数据
	 * @return - 密文数据
	 */
	public String cryptDes(String source) {
		// 对称加密
		return Des3Encryption.encode(source);
	}

	/**
	 * 按照Triple-DES算法，使用系统固定的密钥"a1b2c3d4e5f6g7h8i9j0klmn"，对数据进行解密
	 * 
	 * @param des - 密文数据
	 * @return - 明文数据
	 */
	public String decryptDes(String des) {
		// 对称解密
		return Des3Encryption.decode(des);
	}

	/**
	 * 对数据进行MD5签名
	 * 
	 * @param source - 待签名数据
	 * @param key - 密钥
	 * @return - 数据签名结果
	 */
	public String cryptMd5(String source, String key) {
		byte[] kIpad = new byte[64];
		byte[] kOpad = new byte[64];
		byte[] keyb;
		byte[] value;
		try {
			keyb = key.getBytes("UTF-8");
			value = source.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			keyb = key.getBytes();
			value = source.getBytes();
		}

		Arrays.fill(kIpad, keyb.length, 64, (byte) 54);
		Arrays.fill(kOpad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			kIpad[i] = (byte) (keyb[i] ^ 0x36);
			kOpad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// Log.alert(com.mbi.security.Digest.class, (byte)1,
// "Cannot get algorithm", e);
			return null;
		}
		md.update(kIpad);
		md.update(value);
		byte[] dg = md.digest();
		md.reset();
		md.update(kOpad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	/**
	 * 数据转成十六进制
	 * @param input 待转换字符串
	 */
	public static String toHex(byte[] input) {
		if (input == null) {
	        return null;
        }
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16) {
	            output.append("0");
            }
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * @param args 所需要参数
	 */
	public static void main(String[] args) {
		String source = "12345678";
		String key = "123456781234567812345678";
		CryptUtilImpl impl = new CryptUtilImpl();
		String des = impl.cryptDes(source, key);
		System.out.println(des);

		String s1 = impl.decryptDes(des, key);
		System.out.println(s1);
	}

}
