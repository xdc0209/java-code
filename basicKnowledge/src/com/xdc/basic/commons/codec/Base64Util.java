package com.xdc.basic.commons.codec;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

import com.xdc.basic.commons.BytesUtil;

public class Base64Util
{
    private static final Base64 base64 = new Base64();

    /**
     * 使用Base64加密。
     */
    public static String encodeStr(String plainText, String charsetName)
    {
        return encodeStr(plainText, Charset.forName(charsetName));
    }

    /**
     * 使用Base64加密。
     */
    public static String encodeStr(String plainText, Charset charset)
    {
        byte[] bytes = BytesUtil.getBytes(plainText, charset);
        bytes = base64.encode(bytes);
        return BytesUtil.newString(bytes, charset);
    }

    /**
     * 使用Base64解密。
     */
    public static String decodeStr(String encodedStr, String charsetName)
    {
        return decodeStr(encodedStr, Charset.forName(charsetName));
    }

    public static String decodeStr(String encodedStr, Charset charset)
    {
        byte[] bytes = BytesUtil.getBytes(encodedStr, charset);
        bytes = base64.decode(bytes);
        return BytesUtil.newString(bytes, charset);
    }
}
