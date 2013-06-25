package com.xdc.basic.skills.encrypt;

import java.util.Map;

/**
 * 本例展示加密的一般用法。
 * 
 * AES的优点是比较快，缺点就是密钥的保密很关键，在管理上也是一个问题，因为太多的密钥也不太好保密和管理；
 * 而RSA则相对要慢一点， * 非对称下面的RSA公钥和密钥也要安全一些 * 。对使用者来说，只要能方便的处理就可以了，
 * 最终的使用者并不一定关心它们的算法实现过程，所以对使用者来说，AES只需要让用户设置一个password，
 * 然后就可以得到加密密文和进行解密，RSA则只需要掌握一个密钥和公钥，不需要掌握太多的东西，否则就事倍功半，得不偿失。
 * 一般情况下，加密和解密不会只采用单独的一种加密算法，为保证安全，很多时候都是将多个不同的加密算法进行交叉组合，
 * 增加破解的难度，如先进行BASE64转换，然后用DES或AES来进行加密，再进行RSA加密，这样即使你破解了其中的一种，
 * 但是你还得花精力将其它的加密过程全部破解才能得到明文，从概率统计上来说，能同时破解几种算法的可能性是比较小的，
 * 这样就保证了加密的安全性。
 * 
 * @author xdc
 * 
 */
public class CommonUsage
{
    public static void main(String[] args) throws Exception
    {
        String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
        System.out.println("原文：" + source);

        // ==========================================================================
        // A要发消息给B，B生成密钥对，并将公钥发送给A ===================================
        // ==========================================================================
        Map<String, String> keyPairMap = RSACodec.genKeyPair();
        String publicKey = RSACodec.getPublicKey(keyPairMap);
        String privateKey = RSACodec.getPrivateKey(keyPairMap);

        // ==========================================================================
        // A使用AES加密数据、RSA加密AES密钥，并将加密后的数据和密钥发送给B =============
        // ==========================================================================
        // 使用AES算法加密数据
        String secretKey = AESCodec.genKey();
        String encryptData = AESCodec.encrypt(source, secretKey);
        System.out.println("加密：" + encryptData);
        // 使用RSA算法加密AES算法的密钥
        String encryptSecretKey = RSACodec.encrypt(secretKey, publicKey);

        // ==========================================================================
        // B使用RSA私钥解密AES的密钥，在用AES解密数据 =================================
        // ==========================================================================
        // 使用RSA算法解密AES算法的密钥
        String decryptSecretKey = RSACodec.decrypt(encryptSecretKey, privateKey);
        String decryptData = AESCodec.decrypt(encryptData, decryptSecretKey);
        System.out.println("解密：" + decryptData);

    }
}
