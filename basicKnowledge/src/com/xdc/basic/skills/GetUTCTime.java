package com.xdc.basic.skills;

import java.util.Calendar;

public class GetUTCTime
{
	public static void main(String[] args)
	{
		// 1、取得本地时间：
		final Calendar cal = Calendar.getInstance();
		System.out.println("1、本地时间：" + cal.getTime());

		// 2、取得时间偏移量：
		final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		System.out.println("2、时间偏移量：" + zoneOffset);

		// 3、取得夏令时差：
		final int dstOffset = cal.get(Calendar.DST_OFFSET);
		System.out.println("3、夏令时差：" + dstOffset);

		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		System.out.println("4、UTC时间：" + cal.getTime());
	}
}
