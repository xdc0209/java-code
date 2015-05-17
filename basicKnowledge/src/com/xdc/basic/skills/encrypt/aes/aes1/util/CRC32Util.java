package com.xdc.basic.skills.encrypt.aes.aes1.util;

import java.util.zip.CRC32;

import org.apache.commons.lang3.StringUtils;

public class CRC32Util
{
    /**
     * CRC32结果为32bit，4个字节，Hex长度为8
     */
    public static String crc(byte[] bytes)
    {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return Long.toHexString(crc32.getValue() & 0xffffffff);
    }

    public static boolean match(byte[] bytes, String crcToMatch)
    {
        String crc = CRC32Util.crc(bytes);
        return StringUtils.equals(crc, crcToMatch);
    }

    //    public static void main(String[] args)
    //    {
    //        String s = "I'm the king of world!";
    //        System.out.println(CRC32Util.crc(s.getBytes()));
    //    }
}
