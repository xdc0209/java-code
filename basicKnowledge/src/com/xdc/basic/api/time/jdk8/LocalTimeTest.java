package com.xdc.basic.api.time.jdk8;

import java.time.LocalTime;

/**
 * LocalTime只包含时间，以前用java.util.Date怎么才能只表示时间呢？答案是，假装忽略日期。
 * 
 * 参考：https://www.liaoxuefeng.com/article/00141939241051502ada88137694b62bfe844cd79e12c32000
 * 参考：https://my.oschina.net/benhaile/blog/193956
 */
public class LocalTimeTest
{
    public static void main(String[] args)
    {
        // 获取当前时间(包含毫秒)。
        LocalTime time1 = LocalTime.now();
        System.out.println("time1: " + time1);

        // 获取当前时间(不包含毫秒)。
        LocalTime time2 = LocalTime.now().withNano(0);
        System.out.println("time2: " + time2);

        // 获取指定时间。
        LocalTime time3 = LocalTime.of(23, 59, 59);
        System.out.println("time3: " + time3);

        // 解析指定时间。按照ISO格式解析，可以识别以下3种格式：
        // 23:59
        // 23:59:59
        // 23:59:59.999
        LocalTime time4 = LocalTime.parse("23:59:59");
        System.out.println("time4: " + time4);
    }
}
