package com.xdc.basic.skills.encrypt.aes.aes1.core;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.skills.encrypt.aes.aes1.util.CodecUtil;
import com.xdc.basic.skills.encrypt.aes.aes1.util.RandomUtil;

public class Enc
{
    public static String encode(String plainText, String keyHex) throws EncException
    {
        try
        {
            CipherSuit cipherSuit = CipherSuit.AES_CBC_PKCS5Padding;
            byte[] plainBytes = CodecUtil.string2Bytes(plainText);
            byte[] keyBytes = CodecUtil.hexString2Bytes(keyHex);
            byte[] ivBytes = RandomUtil.randomBytes(16);

            byte[] cipherBytes = encode(cipherSuit, plainBytes, keyBytes, ivBytes);

            // 将加密结果的字节数组进行编码(主要编解码方式有Base64, HEX, UUE),
            String cipherText = String.format("%s:%s:%s", cipherSuit.toHexString(), CodecUtil.bytes2HexString(ivBytes),
                    CodecUtil.bytes2HexString(cipherBytes));
            return cipherText;
        }
        catch (Exception e)
        {
            throw new EncException("Encode failed.", e);
        }
    }

    public static String decode(String cipherText, String keyHex) throws EncException
    {
        try
        {
            String[] split = StringUtils.split(cipherText, ":");
            if (split == null || split.length != 3)
            {
                return null;
            }

            String cipherSuitHex = split[0];
            String ivHex = split[1];
            String cipherHex = split[2];

            CipherSuit cipherSuit = CipherSuit.fromHexString(cipherSuitHex);
            if (cipherSuit == null)
            {
                throw new EncException("The cipher suit in cipher text is not valid.");
            }

            byte[] ivBytes = CodecUtil.hexString2Bytes(ivHex);
            byte[] keyBytes = CodecUtil.hexString2Bytes(keyHex);
            byte[] cipherBytes = CodecUtil.hexString2Bytes(cipherHex);

            if (keyBytes.length != 16)
            {
                throw new EncException("IV length must be 16 bytes long.");
            }

            byte[] plainBytes = decode(cipherSuit, cipherBytes, keyBytes, ivBytes);

            String plainText = CodecUtil.bytes2String(plainBytes);
            return plainText;
        }
        catch (Exception e)
        {
            throw new EncException("Decode failed.", e);
        }
    }

    private static byte[] encode(CipherSuit cs, byte[] plainBytes, byte[] keyBytes, byte[] ivBytes)
            throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        return doFinal(Cipher.ENCRYPT_MODE, cs.toString(), cs.getAlgorithm(), plainBytes, keyBytes, ivBytes);
    }

    private static byte[] decode(CipherSuit cs, byte[] cipherBytes, byte[] keyBytes, byte[] ivBytes)
            throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        return doFinal(Cipher.DECRYPT_MODE, cs.toString(), cs.getAlgorithm(), cipherBytes, keyBytes, ivBytes);
    }

    private static byte[] doFinal(int mode, String cipherParam, String keyParam, byte[] inputBytes, byte[] keyBytes,
            byte[] ivBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
    {
        Cipher cipher = Cipher.getInstance(cipherParam);

        // 秘钥, AES必须为16/24/32bytes, 即: 128/192/256bits. 美国对加密解密等软件进行了出口限制，JDK中默认支持的的密钥长度较短(128bits)，加密强度较低，而UnlimitedJCEPolicyJDK7中的文件则没有这样的限制，因此为了获得更好的加密强度，可以到JDK官网下载此文件并替换掉JDK中对应的文件。
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, keyParam);

        // 初始化向量, AES必须为16bytes, 即: 128bits.
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        cipher.init(mode, keySpec, ivSpec);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        return outputBytes;
    }

    /**
     * 秘钥导出算法
     * 
     * @throws EncException
     */
    public static byte[] encodeWithPBKDF2(char[] text, byte[] salt, String algorithm, int keyLength, int iterationCount)
            throws EncException
    {
        try
        {
            PBEKeySpec keySpec = new PBEKeySpec(text, salt, iterationCount, keyLength);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);

            byte[] hashText = secretKey.getEncoded();
            keySpec.clearPassword();

            return hashText;
        }
        catch (Exception e)
        {
            throw new EncException("encode with PBKDF2 failed.", e);
        }
    }
}
