package sxt.com.crypt;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * ANSI X9.9 MAC校验算法
 */
public class MessageAuthenticationCode {
//  private static final String Algorithm = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish

	/**
	 * 报文MAC处理，参照ANSI X9.9标准
	 * 
	 * @param key 加密密钥
	 * @param data 待加密数据
	 * @return
	 * @throws Exception 各种异常
	 */
	public static byte[] mac(byte[] key, byte[] data) throws Exception {

		return mac(key, data, 0, data.length);
	}

	/**
	 * 报文MAC处理，参照ANSI X9.9标准
	 * 
	 * @param key -- 密钥（长度8字节）
	 * @param data -- 需要做MAC的数据
	 * @param offset -- data的起始位置
	 * @param len -- 需要MAC的数据长度
	 * @throws Exception 各种异常
	 * @return
	 */
	public static byte[] mac(byte[] key, byte[] data, int offset, int len) throws Exception {
		final String algorithm = "DES"; // 定义 加密算法,可用 DES,DESede,Blowfish

		// 生成密钥
		SecretKey deskey = new SecretKeySpec(key, algorithm);

		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);

		byte[] buf = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
		for (int i = 0; i < len;) {
			for (int j = 0; j < 8 && i < len; i++, j++) {
				buf[j] ^= data[offset + i];
			}
			buf = c1.update(buf);
		}
		c1.doFinal();
		return buf;
	}

	/**
	 * DES加密处理
	 * 注意：算法必须使用"DES/ECB/NoPadding"才能产生8bytes的加密结果
	 * 
	 * @param key 加密密匙
	 * @param data 待加密数据
	 * @return
	 * @throws java.lang.Exception 各种异常
	 */
	public static byte[] desEncryption(byte[] key, byte[] data) throws Exception {
		final String algorithm = "DES/ECB/NoPadding"; // 定义 加密算法,可用
// DES,DESede,Blowfish

		if (key.length != DESKeySpec.DES_KEY_LEN || data.length != 8) {
			throw new IllegalArgumentException("key or data's length != 8");
		}

		// 生成密钥
		DESKeySpec desKS = new DESKeySpec(key);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey deskey = skf.generateSecret(desKS);

		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);

		byte[] buf;
		// 如果直接使用doFinal()，将返回16字节的结果，头8个字节与update相同
		buf = c1.doFinal(data);

		// 仅仅返回8字节的des数据
		byte[] encData = new byte[8];
		System.arraycopy(buf, 0, encData, 0, 8);
		return encData;
	}

	/**
	 * DES解密处理
	 * 注意：算法必须使用"DES/ECB/NoPadding"才能产生8bytes的加密结果
	 * 
	 * @param key 加密密匙
	 * @param data 待加密数据
	 * @return
	 * @throws java.lang.Exception 其它异常
	 */
	public static byte[] desDecryption(byte[] key, byte[] data) throws Exception {
		final String algorithm = "DES/ECB/NoPadding"; // 定义 加密算法,可用
// DES,DESede,Blowfish

		if (key.length != DESKeySpec.DES_KEY_LEN || data.length != 8) {
			throw new IllegalArgumentException("key's len != 8 or data's length != 8");
		}

		// 生成密钥
		SecretKey deskey = new SecretKeySpec(key, "DES");

		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);

		byte[] decrypted;

		// 如果直接使用doFinal()，将返回16字节的结果，头8个字节与update相同
		decrypted = c1.doFinal(data);
//    System.out.println("decrypted = " + StringArrayUtil.byte2hex(decrypted));

		return decrypted;
	}

	/**
	 * 用DES方法加密输入的字节
	 * bytKey需为8字节长，是加密的密码
	 * 
	 * @param bytP 待加密数据
	 * @param bytKey 加密密匙
	 * @return
	 * @throws Exception 各种异常
	 */
	protected byte[] encryptByDES(byte[] bytP, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.ENCRYPT_MODE, sk);
		return cip.doFinal(bytP);
	}

	/**
	 * 用DES方法解密输入的字节
	 * bytKey需为8字节长，是解密的密码
	 * 
	 * @param bytE 待加密数据
	 * @param bytKey 加密密匙
	 * @return
	 * @throws Exception 各种异常
	 */
	protected byte[] decryptByDES(byte[] bytE, byte[] bytKey) throws Exception {
		DESKeySpec desKS = new DESKeySpec(bytKey);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey sk = skf.generateSecret(desKS);
		Cipher cip = Cipher.getInstance("DES");
		cip.init(Cipher.DECRYPT_MODE, sk);
		return cip.doFinal(bytE);
	}

	/**
	 * 字符串 DESede(3DES) 加密
	 * 
	 * @param key 加密密匙
	 * @param data 待加密数据
	 * @return
	 * @throws Exception 各种异常
	 */
	public static byte[] des3Encryption(byte[] key, byte[] data) throws Exception {
		final String algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish

		// 生成密钥
		SecretKey deskey = new SecretKeySpec(key, algorithm);

		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(data);
	}

	/**
	 * 字符串 DESede(3DES) 解密
	 * 
	 * @param key 加密密钥
	 * @param data 待加密数据
	 * @return
	 * @throws Exception 各种异常
	 */
	public static byte[] des3Decryption(byte[] key, byte[] data) throws Exception {
		final String algorithm = "DESede"; // 定义 加密算法,可用 DES,DESede,Blowfish

		// 生成密钥
		SecretKey deskey = new SecretKeySpec(key, algorithm);

		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		return c1.doFinal(data);
	}

	/**
	 * 对数据进行3DES加密
	 * 
	 * @param key - 24 bytes的密钥（三组）
	 * @param iv - 加密数据使用的random向量(8 bytes)
	 * @param data - 待加密的数据
	 * @return 加密后的数据
	 * @throws Exception 各种异常
	 */
	public static byte[] des3Encryption(byte[] key, byte[] iv, byte[] data) throws Exception {
		final String algorithm = "DESede/CBC/PKCS5Padding"; // 定义 加密算法,可用
// DES,DESede,Blowfish
		// 生成密钥
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKey deskey = keyFactory.generateSecret(spec);
		// SecretKey deskey = new SecretKeySpec(key, Algorithm);
		// iv
		IvParameterSpec tempIv = new IvParameterSpec(iv);
		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey, tempIv);
		return c1.doFinal(data);

	}

	/**
	 * 对数据进行3DES解密
	 * 
	 * @param key - 24 bytes的密钥（三组）
	 * @param iv - 加密数据使用的random向量(8 bytes)
	 * @param data - 待解密的数据（8的倍数）
	 * @return - 解密后的数据
	 * @throws Exception 各种异常
	 */
	public static byte[] des3Decryption(byte[] key, byte[] iv, byte[] data)
	        throws Exception {
		final String algorithm = "DESede/CBC/PKCS5Padding"; // 定义 加密算法,可用
// DES,DESede,Blowfish
		// 生成密钥
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKey deskey = keyFactory.generateSecret(spec);
		// SecretKey deskey = new SecretKeySpec(key, Algorithm);
		// iv
		IvParameterSpec tempIv = new IvParameterSpec(iv);
		// 加密
		Cipher c1 = Cipher.getInstance(algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey, tempIv);
		return c1.doFinal(data);

	}

	/**
	 * Main方法
	 * @param args 键盘输入参数
	 * @throws Exception 各种异常
	 */
	public static void main(String[] args) throws Exception {
		String key = "6647b807e97889fca8b60047e85d9186e380d3c234f71566";
		byte[] keyb = StringArrayUtil.hex2byte(key, key.length());
		String iv = "9df131b13df6bdfe";
		byte[] ivb = StringArrayUtil.hex2byte(iv, iv.length());
		String value = "2032309250345045,500";
		// byte[]temp_value =StringArrayUtil.hex2byte(value, value.length());
		byte[] tempBytes = des3Encryption(keyb, ivb, value.getBytes("UTF-8"));

		System.out.println("en=" + StringArrayUtil.byte2hex(tempBytes));

		byte[] decrptBytes = des3Decryption(keyb, ivb, StringArrayUtil.hex2byte(
		        "da0dde2d75cef459faa40a068232cd2eaddf0c7bcd7ef53d",
		        "da0dde2d75cef459faa40a068232cd2eaddf0c7bcd7ef53d".length()));
		System.out.println("de=" + new String(decrptBytes, "UTF-8"));

		String md5key = "66ea3f65-382a-44f6-97e4-15b0a873332f";

		String tempMD5 = "PICODE=PI00001CARDDATA=" + StringArrayUtil.byte2hex(tempBytes) + md5key;
		System.out.println(tempMD5);

		String md5 = Digest.hmacSign(tempMD5);
		System.out.println(md5);

	}
}