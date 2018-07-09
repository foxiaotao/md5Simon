package sxt.com.crypt_aes;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * 基于RSA的SHA-1签名
 * 利用rsa生成一对公私钥
 * SHA1withRSA进行签名与验签
 *
 * 参考：http://blog.csdn.net/linkunhao123/article/details/50378263
 * 参考：https://docs.oracle.com/javase/tutorial/security/apisign/index.html
 *
 * @author renwc
 * @date 2018-02-12
 */
public class RSAUtil {
    static final Map<String, String> KEY_PAIR = new HashMap<String, String>(2);
    static final String PUBLIC_KEY = "publicKey";
    static final String PRIVATE_KEY = "privateKey";
    static final String KEY_ALGORITHM = "RSA";
    static final String KEY_SIGNATURE = "SHA1withRSA";
    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
    static final int KEY_SIZE = 2048;
    /** 默认字符编码 */
    static final String CHAR_ENCODING = "UTF-8";
    /** /算法/模式/补码方式 */
    static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 生成公钥和私钥
     */
    static void init(){
        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            gen.initialize(KEY_SIZE);
            KeyPair pair = gen.generateKeyPair();
            //rsa生成一对公私钥
            PublicKey publicKey  = pair.getPublic();
            PrivateKey privateKey  = pair.getPrivate();
            KEY_PAIR.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            KEY_PAIR.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param publicKey - Base64 String
     * @return PublicKey
     */
    static PublicKey restorePublicKey(String publicKey) {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey key = factory.generatePublic(x509EncodedKeySpec);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param privateKey - Base64 String
     * @return PrivateKey
     */
    static PrivateKey restorePrivateKey(String privateKey) {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey key = factory.generatePrivate(pkcs8EncodedKeySpec);
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名
     * @param data - 数据
     * @param privateKey - Base64 String
     * @return 签名
     */
    public static String sign(String data, String privateKey) throws Exception {
        //获取私钥
        PrivateKey key  = restorePrivateKey(privateKey);
        //SHA1withRSA算法 - 进行签名
        Signature sign = Signature.getInstance(KEY_SIGNATURE);
        //初始化这个用于签名的对象
        sign.initSign(key);
        //更新用于签名的数据
        sign.update(data.getBytes(Charset.forName(CHAR_ENCODING)));
        //数据的签名/指纹
        byte[] signature = sign.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    /**
     * 验签
     * @param data - 数据
     * @param sign - 签名
     * @param publicKey - Base64 String
     * @return 结果
     */
    public static boolean verify(String data, String sign, String publicKey) throws Exception {
        //获取公钥
        PublicKey key  = restorePublicKey(publicKey);
        //SHA1withRSA算法 - 进行验证
        Signature verifySign = Signature.getInstance(KEY_SIGNATURE);
        //初始化此用于验证的对象
        verifySign.initVerify(key);
        //用于验签的数据
        verifySign.update(data.getBytes(Charset.forName(CHAR_ENCODING)));
        //验证签名
        boolean verify = verifySign.verify(Base64.getDecoder().decode(sign));
        return verify;
    }

    /**
     * 加密 - RSA
     * @param source - 源数据
     * @return 加密数据
     */
    public static String encrypt(String source, String publicKey) throws Exception {
        Key key = RSAUtil.restorePublicKey(publicKey);
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] encrypt = cipher.doFinal(b);
        return Base64.getEncoder().encodeToString(encrypt);
    }

    /**
     * 解密 - RSA
     * @param data - 加密数据
     * @return 源数据
     */
    public static String decrypt(String data, String privateKey) throws Exception {
        Key key = RSAUtil.restorePrivateKey(privateKey);
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encrypt = Base64.getDecoder().decode(data.getBytes());
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(encrypt);
        return new String(b);
    }

    public static void main(String[] args) throws IOException {
        init();
        System.out.println(KEY_PAIR.get(PUBLIC_KEY));
        System.out.println(KEY_PAIR.get(PRIVATE_KEY));
        //原始数据
        String source = "中关村e世界@大狗";
        System.out.println(source);
        try {
            String data = encrypt(source, KEY_PAIR.get(PUBLIC_KEY));
            String sign = sign(data, KEY_PAIR.get(PRIVATE_KEY));
            boolean verify = verify(data, sign, KEY_PAIR.get(PUBLIC_KEY));
            source = decrypt(data, KEY_PAIR.get(PRIVATE_KEY));
            System.out.println(data);
            System.out.println(sign);
            System.out.println(verify);
            System.out.println(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}