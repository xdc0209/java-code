package com.xdc.basic.api.time.jdk8;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;

/**
 * 参考：https://www.liaoxuefeng.com/article/00141939241051502ada88137694b62bfe844cd79e12c32000
 * 参考：https://my.oschina.net/benhaile/blog/193956
 */
public class LocalDateTimeTest
{
    public static void main(String[] args) throws InterruptedException
    {
        // 获取当前时间(默认时区)。
        LocalDateTime dt1 = LocalDateTime.now();
        System.out.println("dt1: " + dt1);

        // 获取当前时间(自定义时区)。
        LocalDateTime dt2 = LocalDateTime.now(ZoneId.of("Europe/Paris"));
        System.out.println("dt2: " + dt2);

        // 获取指定时间。
        LocalDateTime dt3 = LocalDateTime.of(2018, 12, 31, 23, 59, 59, 999 * 1000 * 1000);
        System.out.println("dt3: " + dt3);

        // 解析指定时间。按照ISO格式解析，可以识别以下3种格式：
        // 2018-12-31T23:59
        // 2018-12-31T23:59:59
        // 2018-12-31T23:59:59.999
        LocalDateTime dt4 = LocalDateTime.parse("2018-12-31T23:59:59.999");
        System.out.println("dt4: " + dt4);

        // 使用DateTimeFormatter解析和格式化。
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dt5 = LocalDateTime.parse("2018/12/31 23:59:59", formatter);
        System.out.println("dt5: " + formatter.format(dt5));

        // 时间获取。
        System.out.println(dt5.getYear());
        System.out.println(dt5.getMonth());
        System.out.println(dt5.getDayOfYear());
        System.out.println(dt5.getDayOfMonth());
        System.out.println(dt5.getDayOfWeek());
        System.out.println(dt5.getHour());
        System.out.println(dt5.getMinute());
        System.out.println(dt5.getSecond());
        System.out.println(dt5.getNano());

        // 时间增减。
        LocalDateTime dt6 = dt5.minusDays(1);
        System.out.println("dt6: " + dt6);
        LocalDateTime dt7 = dt6.plus(1, IsoFields.QUARTER_YEARS);
        System.out.println("dt7: " + dt7);
    }
}
