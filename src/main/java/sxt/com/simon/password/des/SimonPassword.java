package sxt.com.simon.password.des;

import sxt.com.crypt.CryptUtilImpl;

public class SimonPassword {

	private static String key = "123456781234567812345678";//24wei
	public static void main(String[] args) {
		CryptUtilImpl crypt = new CryptUtilImpl();
//		String des = crypt.cryptDes("192089", key); 
		System.out.println(crypt.cryptDes("192090", key));
		//System.out.println(crypt.cryptDes("gveuxKm9b2Q==", key));
		
		System.out.println(crypt.decryptDes("3HDPtqdOODA=", key));
	}
	/**
	 * 加密
	 * @param mingwen
	 * @return
	 */
	public static String enPassword(String mingwen){
		CryptUtilImpl crypt = new CryptUtilImpl();
		return crypt.cryptDes("192090", key);
	}
	/**
	 * 解密
	 * @param mingwen
	 * @return
	 */
	public static String denPassword(String miwen){
		CryptUtilImpl crypt = new CryptUtilImpl();
		return crypt.decryptDes("SnFmGYME5Ao=", key);
	}
	
}
