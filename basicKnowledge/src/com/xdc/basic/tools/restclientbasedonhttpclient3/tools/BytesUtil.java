package com.xdc.basic.tools.restclientbasedonhttpclient3.tools;

import java.io.UnsupportedEncodingException;

public class BytesUtil
{
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