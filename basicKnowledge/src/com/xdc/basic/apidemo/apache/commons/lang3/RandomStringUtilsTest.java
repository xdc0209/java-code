package com.xdc.basic.apidemo.apache.commons.lang3;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 随机生成字符串
 * 
 * @author xdc
 * 
 */
public class RandomStringUtilsTest
{
	public static void main(String[] args)
	{
		// 生成6位随机ascii串
		System.out.println(RandomStringUtils.randomAscii(6));
		// 生成6位随机字母串
		System.out.println(RandomStringUtils.randomAlphabetic(6));
		// 生成6位随机字母数字串
		System.out.println(RandomStringUtils.randomAlphanumeric(6));
		// 生成6位随机数字串
		System.out.println(RandomStringUtils.randomNumeric(6));
		// 生成6位随机数字串,指定字符
		System.out.println(RandomStringUtils.random(6, "xdc0209"));
	}
}
