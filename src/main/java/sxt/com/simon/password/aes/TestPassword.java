package sxt.com.simon.password.aes;

import sxt.com.crypt_aes.AESUtil;

/**
 * Created with suntao on 2018/7/9
 */
public class TestPassword {


    private static String aesKey = "2280219900726171";

    public static void main(String[] args) {
        String encriptData = "192094";
        String decriptData = "mpYwCt1VSp7pxrrs30J7JQ==";

        System.out.println("encrypt:"+ AESUtil.encryptAndBase64Encode(encriptData, aesKey));
        System.out.println("encrypt:"+ AESUtil.decryptAfterBase64Decode(decriptData, aesKey));
    }
}
