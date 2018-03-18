package com.xdc.basic.commons;

import java.nio.charset.Charset;

import com.xdc.basic.commons.codec.Charsets;

public class BytesUtil
{
    /**
     * 根据指定编码将字符串转换成字节数组。
     */
    public static byte[] getBytes(String string, String charsetName)
    {
        return getBytes(string, Charset.forName(charsetName));
    }

    /**
     * @see org.apache.commons.io.Charsets
     */
    public static byte[] getBytes(String string, Charset charset)
    {
        return string == null ? null : string.getBytes(charset);
    }

    /**
     * @see org.apache.commons.codec.binary.StringUtils.getBytesUtf8(String)
     */
    public static byte[] getBytesUtf8(String string)
    {
        return getBytes(string, Charsets.UTF_8);
    }

    /**
     * 根据指定编码将字节数组转换成字符串。
     */
    public static String newString(byte[] bytes, String charsetName)
    {
        return newString(bytes, Charset.forName(charsetName));
    }

    /**
     * @see org.apache.commons.io.Charsets
     */
    public static String newString(byte[] bytes, Charset charset)
    {
        return bytes == null ? null : new String(bytes, charset);
    }

    /**
     * @see org.apache.commons.codec.binary.StringUtils.newStringUtf8(byte[])
     */
    public static String newStringUtf8(byte[] bytes)
    {
        return newString(bytes, Charsets.UTF_8);
    }
}
