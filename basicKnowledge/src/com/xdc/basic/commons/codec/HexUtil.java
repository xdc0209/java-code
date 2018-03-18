package com.xdc.basic.commons.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.xdc.basic.commons.ExceptionUtil;

public class HexUtil
{
    public static String bytes2HexString(byte[] bytes)
    {
        return bytes == null ? null : Hex.encodeHexString(bytes);
    }

    public static byte[] hexString2Bytes(String hexString)
    {
        try
        {
            return hexString == null ? null : Hex.decodeHex(hexString.toCharArray());
        }
        catch (DecoderException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }
}
