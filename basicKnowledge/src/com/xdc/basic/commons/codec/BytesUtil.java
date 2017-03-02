package com.xdc.basic.commons.codec;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class BytesUtil
{
    public static byte[] getBytes(String string, Charset charset)
    {
        return string == null ? null : string.getBytes(charset);
    }

    public static String newString(byte[] bytes, Charset charset)
    {
        return bytes == null ? null : new String(bytes, charset);
    }

    /**
     * 根据指定编码将字符串转换成字节数组
     * 
     * @param plainText
     * @param charsetName
     * @return
     */
    public static byte[] string2Bytes(String plainText, String charsetName)
    {
        // apache提供了专门的方法去获取字符串的utf8编码的字节数组
        // return StringUtils.getBytesUtf8(plainText);

        byte[] b = null;
        try
        {
            b = plainText.getBytes(charsetName);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 根据指定编码将字节数组转换成字符串
     * 
     * @param b
     * @param charsetName
     * @return
     */
    public static String bytes2String(byte[] b, String charsetName)
    {
        // apache提供了专门的方法从字节数组转换成utf8编码的字符串
        // return StringUtils.newStringUtf8(b);

        String s = null;
        try
        {
            s = new String(b, charsetName);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return s;
    }
}