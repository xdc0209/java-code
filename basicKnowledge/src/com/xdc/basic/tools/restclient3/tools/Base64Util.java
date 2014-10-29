package com.xdc.basic.tools.restclient3.tools;

import org.apache.commons.codec.binary.Base64;

public class Base64Util
{
    public static final String  UTF8   = "UTF-8";     // UTF8和UTF-8一样 
    public static final String  GBK    = "GBK";
    private static final Base64 base64 = new Base64();

    /**
     * 使用Base64加密
     * 
     * @param plainText
     * @param charsetName
     * @return
     */
    public static String encodeStr(String plainText, String charsetName)
    {
        byte[] b = BytesUtil.string2Bytes(plainText, charsetName);
        b = base64.encode(b);
        return BytesUtil.bytes2String(b, charsetName);
    }

    /**
     * 使用Base64解密
     * 
     * @param encodeStr
     * @param charsetName
     * @return
     */
    public static String decodeStr(String encodeStr, String charsetName)
    {
        byte[] b = BytesUtil.string2Bytes(encodeStr, charsetName);
        b = base64.decode(b);
        return BytesUtil.bytes2String(b, charsetName);
    }
}