package com.xdc.basic.skills.encrypt.aes.aes1.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import com.xdc.basic.skills.encrypt.aes.aes1.core.EncException;

/**
 * Codec 翻译为编码解码器
 */
public class CodecUtil
{
    public static String bytes2HexString(byte[] bytes)
    {
        return Hex.encodeHexString(bytes);
    }

    public static byte[] hexString2Bytes(String hexString) throws EncException
    {
        if (StringUtils.isBlank(hexString))
        {
            return null;
        }

        try
        {
            return Hex.decodeHex(hexString.toCharArray());
        }
        catch (DecoderException e)
        {
            throw new EncException(e);
        }
    }

    /**
     * 根据指定编码将字符串转换成字节数组
     */
    public static byte[] string2Bytes(String string) throws EncException
    {
        // apache提供了专门的方法去获取字符串的utf8编码的字节数组
        // return StringUtils.getBytesUtf8(plainText);

        byte[] bytes = null;
        try
        {
            bytes = string.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new EncException(e);
        }
        return bytes;
    }

    /**
     * 根据指定编码将字节数组转换成字符串
     */
    public static String bytes2String(byte[] bytes) throws EncException
    {
        // apache提供了专门的方法从字节数组转换成utf8编码的字符串
        // return StringUtils.newStringUtf8(b);

        String string = null;
        try
        {
            string = new String(bytes, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new EncException(e);
        }
        return string;
    }

    /**
     * char转byte
     */
    public static byte[] chars2Bytes(char[] chars)
    {
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();

        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = cs.encode(cb);

        return bb.array();
    }

    /**
     * byte转char
     */
    public static char[] bytes2Chars(byte[] bytes)
    {
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();

        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = cs.decode(bb);

        return cb.array();
    }
}
