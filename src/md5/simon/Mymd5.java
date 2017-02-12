package md5.simon;

import md5.crypt.CryptUtilImpl;

public class Mymd5 {

	private static String key = "123456781234567812345678";//24wei
	public static void main(String[] args) {
		CryptUtilImpl crypt = new CryptUtilImpl();
		String des = crypt.cryptDes("192095", key);
		System.out.println(des);
		
		System.out.println(crypt.decryptDes("SnFmGYME5Ao=", key));
	}
}
