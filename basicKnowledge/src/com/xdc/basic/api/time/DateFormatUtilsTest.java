package com.xdc.basic.api.time;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateFormatUtilsTest
{
    public static void main(String[] args)
    {
        // currentTimeMillis()返回以毫秒为单位的当前时间，返回的是当前时间与协调世界时 1970 年 1 月 1 日午夜之间的时间差（以毫秒为单位测量）。
        // 注意，当返回值的时间单位是毫秒时，值的粒度取决于基础操作系统，并且粒度可能更大。例如，许多操作系统以几十毫秒为单位测量时间。
        // 同一个时间，无论是哪个时区的System.currentTimeMillis();返回值都是一样的。只不过不同时区在格式化输出的时候都会根据时区偏移量转化成本地的时间输出。
        long currentTimeMillis = System.currentTimeMillis();

        System.out.println("本地格式日期输出:");
        System.out.println(DateFormatUtils.format(currentTimeMillis, "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"));
        System.out.println();

        System.out.println("UTC格式日期输出:");
        System.out.println(DateFormatUtils.formatUTC(currentTimeMillis, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        System.out.println();

        // 日期 yyyy-MM-dd yyyy-MM-ddZZ
        System.out.println(DateFormatUtils.ISO_DATE_FORMAT.format(currentTimeMillis));
        System.out.println(DateFormatUtils.ISO_DATE_TIME_ZONE_FORMAT.format(currentTimeMillis));
        System.out.println();

        // 日期和时间 yyyy-MM-dd'T'HH:mm:ss yyyy-MM-dd'T'HH:mm:ssZZ
        System.out.println(DateFormatUtils.ISO_DATETIME_FORMAT.format(currentTimeMillis));
        System.out.println(DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(currentTimeMillis));
        System.out.println();

        // 时间 'T'HH:mm:ss HH:mm:ss
        System.out.println(DateFormatUtils.ISO_TIME_FORMAT.format(currentTimeMillis));
        System.out.println(DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(currentTimeMillis));
        System.out.println();

        // 时间带时区 HH:mm:ssZZ 'T'HH:mm:ssZZ
        System.out.println(DateFormatUtils.ISO_TIME_NO_T_TIME_ZONE_FORMAT.format(currentTimeMillis));
        System.out.println(DateFormatUtils.ISO_TIME_TIME_ZONE_FORMAT.format(currentTimeMillis));
        System.out.println();

        // 邮件头 EEE, dd MMM yyyy HH:mm:ss Z
        System.out.println(DateFormatUtils.SMTP_DATETIME_FORMAT.format(currentTimeMillis));
        System.out.println();
    }
}
