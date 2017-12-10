package com.xdc.basic.skills.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * http://hougbin.iteye.com/blog/713071
 * 
 * @author xdc
 * 
 */
public class RSACodec
{
    // 字符串与字节数组互转时指定编码
    public static final String  UTF8             = "UTF-8";

    // 密钥算法
    private static final String KEY_ALGORITHM    = "RSA";

    // 加解密算法/工作模式/填充方式
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";

    // 获取公钥的key
    private static final String PUBLIC_KEY       = "PublicKey";

    // 获取私钥的key
    private static final String PRIVATE_KEY      = "PrivateKey";

    /**
     * 生成keypair, 得到密钥字符串（经过base64编码）
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, String> genKeyPair() throws Exception
    {
        // 生成密钥对
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM); // 获取keypair生成器
        keyPairGen.initialize(1024); // 设置密钥位数
        KeyPair keyPair = keyPairGen.generateKeyPair(); // 生成keypair
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 构造返回值
        Map<String, String> keyPairMap = new HashMap<String, String>(2);
        keyPairMap.put(PUBLIC_KEY, Base64.encodeBase64String(publicKey.getEncoded()));
        keyPairMap.put(PRIVATE_KEY, Base64.encodeBase64String(privateKey.getEncoded()));

        return keyPairMap;
    }

    /**
     * 获取生成密钥对中的公钥
     * 
     * @param keyPairMap
     * @return
     */
    public static String getPublicKey(Map<String, String> keyPairMap)
    {
        return keyPairMap.get(PUBLIC_KEY);
    }

    /**
     * 获取生成密钥对中的私钥
     * 
     * @param keyPairMap
     * @return
     */
    public static String getPrivateKey(Map<String, String> keyPairMap)
    {
        return keyPairMap.get(PRIVATE_KEY);
    }

    /**
     * 使用公钥加密数据
     * 
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String publicKey) throws Exception
    {
        Key key = restorePublicKey(publicKey); // 还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
        cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化Cipher对象，设置为加密模式
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes(UTF8))); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
    }

    /**
     * 使用私钥解密数据
     * 
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String privateKey) throws Exception
    {
        Key key = restorePrivateKey(privateKey); // 还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
        cipher.init(Cipher.DECRYPT_MODE, key); // 初始化Cipher对象，设置为解密模式
        return new String(cipher.doFinal(Base64.decodeBase64(data)), UTF8); // 执行解密操作
    }

    /**
     * 得到公钥
     * 
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey restorePublicKey(String key) throws Exception
    {
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     * 
     * @param key
     *            密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey restorePrivateKey(String key) throws Exception
    {
        byte[] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static void main(String[] args) throws Exception
    {
        String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
        System.out.println("原文：" + source);

        Map<String, String> keyPairMap = genKeyPair();

        String publicKey = getPublicKey(keyPairMap);
        String privateKey = getPrivateKey(keyPairMap);

        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);

        String encryptData = encrypt(source, publicKey);
        System.out.println("加密：" + encryptData);

        String decryptData = decrypt(encryptData, privateKey);
        System.out.println("解密: " + decryptData);
    }
}
