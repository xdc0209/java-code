package com.xdc.basic.apidemo.apache.commons.lang3.time;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateFormatUtilsTest
{
	public static void main(String[] args)
	{
		System.out.println("本地格式日期输出:");
		System.out.println(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));

		System.out.println("UTC格式日期输出:");
		System.out.println(DateFormatUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	}
}
