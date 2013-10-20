package com.xdc.basic.api.apache.commons.codec.binary;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class HexTest
{
	public static void main(String[] args)
	{
		byte[] raw = StringUtils.getBytesUtf8("You're my baby!");

		char[] encodeHex = Hex.encodeHex(raw);
		String encodeHexString = Hex.encodeHexString(raw);

		try
		{
			byte[] decodeHex = Hex.decodeHex(encodeHex);
			byte[] decodeHexString = Hex.decodeHex(encodeHexString.toCharArray());

			System.out.println(StringUtils.newStringUtf8(decodeHex));
			System.out.println(StringUtils.newStringUtf8(decodeHexString));
		}
		catch (DecoderException e)
		{
			e.printStackTrace();
		}
	}
}
