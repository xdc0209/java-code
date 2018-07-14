package com.xdc.basic.api.time.jdk8;

import java.time.LocalDate;

/**
 * 参考：https://www.liaoxuefeng.com/article/00141939241051502ada88137694b62bfe844cd79e12c32000
 * 参考：https://my.oschina.net/benhaile/blog/193956
 */
public class LocalDateTest
{
    public static void main(String[] args)
    {
        // 获取当前日期。
        LocalDate today = LocalDate.now();
        System.out.println("today: " + today);

        // 获取指定日期。
        LocalDate christmas = LocalDate.of(2018, 12, 25);
        System.out.println("christmas: " + christmas);

        // 解析指定日期。严格按照ISO(yyyy-MM-dd)格式解析，02写成2都不行，当然也有一个重载方法允许自己定义格式。
        LocalDate endOfFeb = LocalDate.parse("2018-02-28");
        System.out.println("endOfFeb: " + endOfFeb);

        // 无效日期，抛出DateTimeParseException异常。
        LocalDate invalidDate = LocalDate.parse("2018-02-29");
        System.out.println("invalidDate: " + invalidDate);
    }
}
