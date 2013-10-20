package com.xdc.basic.api.apache.commons.codec.binary;

import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.StringUtils;

public class BinaryCodecTest
{
	public static void main(String[] args)
	{
		// raw 生的；未加工的；阴冷的；刺痛的
		byte[] raw = StringUtils.getBytesUtf8("You're my baby!");

		// 将字节数组编码为0、1字符串, 不知道为啥结果原始数组的倒序
		byte[] asciiBytes = BinaryCodec.toAsciiBytes(raw);
		char[] asciiChars = BinaryCodec.toAsciiChars(raw);
		String asciiString = BinaryCodec.toAsciiString(raw);

		// 将0、1字符串为字节数组编码
		byte[] raw1 = BinaryCodec.fromAscii(asciiBytes);
		byte[] raw2 = BinaryCodec.fromAscii(asciiChars);
		byte[] raw3 = BinaryCodec.fromAscii(asciiString.toCharArray());

		System.out.println(raw1);
		System.out.println(raw2);
		System.out.println(raw3);
	}
}
