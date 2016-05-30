package com.xdc.basic.skills.encrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * http://www.linuxidc.com/Linux/2012-08/69217.htm
 * 
 * AES对称加密算法
 * 
 * @see ===========================================================================================================
 * @see 这里演示的是其Java6.0的实现,理所当然的BouncyCastle也支持AES对称加密算法
 * @see 另外,我们也可以以AES算法实现为参考,完成RC2,RC4和Blowfish算法的实现
 * @see ===========================================================================================================
 * @see 由于DES的不安全性以及DESede算法的低效,于是催生了AES算法(Advanced Encryption Standard)
 * @see 该算法比DES要快,安全性高,密钥建立时间短,灵敏性好,内存需求低,在各个领域应用广泛
 * @see 目前,AES算法通常用于移动通信系统以及一些软件的安全外壳,还有一些无线路由器中也是用AES算法构建加密协议
 * @see ===========================================================================================================
 * @see 由于Java6.0支持大部分的算法,但受到出口限制,其密钥长度不能满足需求
 * @see 所以特别需要注意的是:如果使用256位的密钥,则需要无政策限制文件(Unlimited Strength Jurisdiction Policy Files)
 * @see 不过Sun是通过权限文件local_poblicy.jar和US_export_policy.jar做的相应限制,我们可以在Sun官网下载替换文件,减少相关限制
 * @see 网址为http://www.Oracle.com/technetwork/java/javase/downloads/index.html
 * @see 在该页面的最下方找到Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6,点击下载
 * @see http://download.oracle.com/otn-pub/java/jce_policy/6/jce_policy-6.zip
 * @see http://download.oracle.com/otn-pub/java/jce/7/UnlimitedJCEPolicyJDK7.zip
 * @see 然后覆盖本地JDK目录和JRE目录下的security目录下的文件即可
 * @see ===========================================================================================================
 * @see 关于AES的更多详细介绍,可以参考此爷的博客http://blog.csdn.net/kongqz/article/category/800296
 * @create Jul 17, 2012 6:35:36 PM
 * @author 玄玉(http://blog.csdn/net/jadyer)
 */
public class AESCodec
{
    // 字符串与字节数组互转时指定编码
    public static final String  UTF8             = "UTF-8";

    // 密钥算法
    private static final String KEY_ALGORITHM    = "AES";

    // 加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 生成密钥字符串 （经过base64编码）
     * 
     * @return
     * @throws Exception
     */
    public static String genKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM); // 实例化密钥生成器
        keyGen.init(128); // 初始化密钥生成器:AES要求密钥长度为128,192,256位
        SecretKey secretKey = keyGen.generateKey(); // 生成密钥
        byte[] secretKeyBytes = secretKey.getEncoded(); // 获取二进制密钥编码形式
        return Base64.encodeBase64String(secretKeyBytes); // 通常都会用Base64编码进行传输
    }

    /**
     * 根据密钥字符串还原key
     * 
     * @param key
     * @return
     * @throws Exception
     */
    private static Key restoreKey(String key) throws Exception
    {
        byte[] keyBytes = Base64.decodeBase64(key);
        return new SecretKeySpec(keyBytes, KEY_ALGORITHM);
    }

    /**
     * 加密数据
     * 
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String secretKey) throws Exception
    {
        Key key = restoreKey(secretKey); // 还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
        cipher.init(Cipher.ENCRYPT_MODE, key); // 初始化Cipher对象，设置为加密模式
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes(UTF8))); // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
    }

    /**
     * 解密数据
     * 
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String secretKey) throws Exception
    {
        Key key = restoreKey(secretKey); // 还原密钥
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM); // 实例化Cipher对象，它用于完成实际的加密操作
        cipher.init(Cipher.DECRYPT_MODE, key); // 初始化Cipher对象，设置为解密模式
        return new String(cipher.doFinal(Base64.decodeBase64(data)), UTF8); // 执行解密操作
    }

    public static void main(String[] args) throws Exception
    {
        String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
        System.out.println("原文：" + source);

        String secretKey = genKey();
        System.out.println("密钥：" + secretKey);

        String encryptData = encrypt(source, secretKey);
        System.out.println("加密：" + encryptData);

        String decryptData = decrypt(encryptData, secretKey);
        System.out.println("解密: " + decryptData);
    }
}