package com.xdc.basic.skills.encrypt.aes.aes2.core;

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

import com.xdc.basic.skills.encrypt.aes.aes2.util.CodecUtil;
import com.xdc.basic.skills.encrypt.aes.aes2.util.RandomUtil;

public class Enc
{
    public static String encode(String plainText, byte[] keyBytes) throws EncException
    {
        return encodeChars(plainText.toCharArray(), keyBytes);
    }

    public static String decode(String cipherText, byte[] keyBytes) throws EncException
    {
        return String.valueOf(decodeChars(cipherText, keyBytes));
    }

    public static String encodeChars(char[] plainChars, byte[] keyBytes) throws EncException
    {
        byte[] plainBytes = CodecUtil.chars2Bytes(plainChars);
        byte[] cipherBytes = encode(plainBytes, keyBytes);
        String cipherText = CodecUtil.bytes2HexString(cipherBytes);
        return cipherText;
    }

    public static char[] decodeChars(String cipherText, byte[] keyBytes) throws EncException
    {
        byte[] cipherBytes = CodecUtil.hexString2Bytes(cipherText);
        byte[] plainBytes = decode(cipherBytes, keyBytes);
        char[] plainText = CodecUtil.bytes2Chars(plainBytes);
        return plainText;
    }

    public static byte[] encode(byte[] plainBytes, byte[] keyBytes) throws EncException
    {
        try
        {
            CipherSuit cipherSuit = CipherSuit.AES_CBC_PKCS5Padding;
            byte[] ivBytes = RandomUtil.randomBytes(16);

            byte[] baseCipherBytes = encode(cipherSuit, plainBytes, keyBytes, ivBytes);

            // 1字节算法编码，16字节iv，最短16字节密文
            byte[] cipherBytes = new byte[1 + 16 + baseCipherBytes.length];
            cipherBytes[0] = cipherSuit.toByte();
            System.arraycopy(ivBytes, 0, cipherBytes, 1, ivBytes.length);
            System.arraycopy(baseCipherBytes, 0, cipherBytes, 1 + 16, baseCipherBytes.length);

            return cipherBytes;
        }
        catch (Exception e)
        {
            throw new EncException("Encode failed.", e);
        }
    }

    public static byte[] decode(byte[] cipherBytes, byte[] keyBytes) throws EncException
    {
        try
        {
            // 1字节算法编码，16字节iv，最短16字节密文
            if (cipherBytes == null || cipherBytes.length < 1 + 16 + 16)
            {
                return null;
            }

            CipherSuit cipherSuit = CipherSuit.fromByte(cipherBytes[0]);
            if (cipherSuit == null)
            {
                throw new EncException("The cipher suit in cipher text is not valid.");
            }

            byte[] ivBytes = new byte[16];
            System.arraycopy(cipherBytes, 1, ivBytes, 0, ivBytes.length);

            byte[] baseCipherBytes = new byte[cipherBytes.length - 1 - 16];
            System.arraycopy(cipherBytes, 1 + 16, baseCipherBytes, 0, baseCipherBytes.length);

            byte[] plainBytes = decode(cipherSuit, baseCipherBytes, keyBytes, ivBytes);
            return plainBytes;
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
    public static byte[] encodeWithPBKDF2(char[] text, byte[] salt) throws EncException
    {
        try
        {
            PBEKeySpec keySpec = new PBEKeySpec(text, salt, 100000, 128);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
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
