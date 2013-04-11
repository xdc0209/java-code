package com.xdc.basic.example.apache.commons.lang3.time;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateFormatUtilsTest
{
	public static void main(String[] args)
	{
		System.out.println("格式化日期输出:");
		System.out.println(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
	}
}
