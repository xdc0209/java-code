package com.xdc.basic.skills.encrypt.aes.aes1.core;

import java.util.HashMap;
import java.util.Map;

// 摘自：http://blog.chinaunix.net/uid-196845-id-2788287.html
// JCE中的AES：
// 支持五中工作模式：CBC，CFB，ECB，OFB，PCBC
// 支持三种填充：NoPadding，PKCS5Padding，ISO10126Padding
public enum CipherSuit
{
    // AES/CBC/NoPadding
    AES_CBC_NoPadding((byte) 1, "AES", "CBC", "NoPadding"),

    // 推荐 AES/CBC/PKCS5Padding
    AES_CBC_PKCS5Padding((byte) 2, "AES", "CBC", "PKCS5Padding"),

    // AES/CBC/ISO10126Padding
    AES_CBC_ISO10126Padding((byte) 3, "AES", "CBC", "ISO10126Padding"),

    // AES/CFB/NoPadding
    AES_CFB_NoPadding((byte) 4, "AES", "CFB", "NoPadding"),

    // AES/CFB/PKCS5Padding
    AES_CFB_PKCS5Padding((byte) 5, "AES", "CFB", "PKCS5Padding"),

    // AES/CFB/ISO10126Padding
    AES_CFB_ISO10126Padding((byte) 6, "AES", "CFB", "ISO10126Padding"),

    // AES/ECB/NoPadding
    AES_ECB_NoPadding((byte) 7, "AES", "ECB", "NoPadding"),

    // AES/ECB/PKCS5Padding
    AES_ECB_PKCS5Padding((byte) 8, "AES", "ECB", "PKCS5Padding"),

    // AES/ECB/ISO10126Padding
    AES_ECB_ISO10126Padding((byte) 9, "AES", "ECB", "ISO10126Padding"),

    // AES/OFB/NoPadding
    AES_OFB_NoPadding((byte) 10, "AES", "OFB", "NoPadding"),

    // AES/OFB/PKCS5Padding
    AES_OFB_PKCS5Padding((byte) 11, "AES", "OFB", "PKCS5Padding"),

    // AES/OFB/ISO10126Padding
    AES_OFB_ISO10126Padding((byte) 12, "AES", "OFB", "ISO10126Padding"),

    // AES/PCBC/NoPadding
    AES_PCBC_NoPadding((byte) 13, "AES", "PCBC", "NoPadding"),

    // AES/PCBC/PKCS5Padding
    AES_PCBC_PKCS5Padding((byte) 14, "AES", "PCBC", "PKCS5Padding"),

    // AES/PCBC/ISO10126Padding
    AES_PCBC_ISO10126Padding((byte) 15, "AES", "PCBC", "ISO10126Padding");

    private byte   index;

    // 加解密算法
    private String algorithm;

    // 工作模式 
    private String workingMode;

    // 填充方式
    private String paddingMode;

    private CipherSuit(byte index, String algorithm, String workingMode, String paddingMode)
    {
        this.index = index;
        this.algorithm = algorithm;
        this.workingMode = workingMode;
        this.paddingMode = paddingMode;
    }

    public String getAlgorithm()
    {
        return algorithm;
    }

    public String getWorkingMode()
    {
        return workingMode;
    }

    public String getPaddingMode()
    {
        return paddingMode;
    }

    // toString ========================================
    @Override
    public String toString()
    {
        return String.format("%s/%s/%s", algorithm, workingMode, paddingMode);
    }

    private static final Map<String, CipherSuit> stringToEnum = new HashMap<String, CipherSuit>();
    static
    {
        for (CipherSuit cipherSuit : values())
        {
            stringToEnum.put(cipherSuit.toString(), cipherSuit);
        }
    }

    public static CipherSuit fromString(String symbol)
    {
        return stringToEnum.get(symbol);
    }

    // toHexString ========================================
    public String toHexString()
    {
        final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        // 一个字节转换成2个字符, 其中字节的高4位在前, 低4位在后
        char[] hexs = new char[2];
        hexs[0] = digits[(0xF0 & index) >>> 4];
        hexs[1] = digits[0x0F & index];

        return new String(hexs);
    }

    private static final Map<String, CipherSuit> hexStringToEnum = new HashMap<String, CipherSuit>();
    static
    {
        for (CipherSuit cipherSuit : values())
        {
            hexStringToEnum.put(cipherSuit.toHexString(), cipherSuit);
        }
    }

    public static CipherSuit fromHexString(String hexString)
    {
        return hexStringToEnum.get(hexString);
    }

    // toByte ========================================
    public byte toByte()
    {
        return index;
    }

    private static final Map<Byte, CipherSuit> byteToEnum = new HashMap<Byte, CipherSuit>();
    static
    {
        for (CipherSuit cipherSuit : values())
        {
            byteToEnum.put(cipherSuit.toByte(), cipherSuit);
        }
    }

    public static CipherSuit fromByte(byte b)
    {
        return byteToEnum.get(b);
    }

    //    public static void main(String[] args)
    //    {
    //        for (CipherSuit cipherSuit : values())
    //        {
    //            byte b = cipherSuit.toByte();
    //            System.out.println(b);
    //            CipherSuit fromByte = CipherSuit.fromByte(b);
    //            System.out.println(cipherSuit == fromByte);
    //
    //            String hexString = cipherSuit.toHexString();
    //            System.out.println(hexString);
    //            CipherSuit fromHexString = CipherSuit.fromHexString(hexString);
    //            System.out.println(cipherSuit == fromHexString);
    //        }
    //    }
}
