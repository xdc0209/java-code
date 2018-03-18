package com.xdc.basic.commons.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.xdc.basic.commons.ExceptionUtil;

public class DateFormatUtil
{
    public static String patternLocalTime       = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"; // 同 DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT

    public static String patternLocalTimeSimple = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String patternUTC             = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * SimpleDateFormat是线程不安全的类，一般不要定义为static变量，每次使用要重新new一个。JDK8可以使用线程安全的DateTimeFormatter，以便重用。
     */
    public static String format(long currentTimeMillis, String pattern)
    {
        return format(new Date(currentTimeMillis), pattern);
    }

    /**
     * SimpleDateFormat是线程不安全的类，一般不要定义为static变量，每次使用要重新new一个。JDK8可以使用线程安全的DateTimeFormatter，以便重用。
     */
    public static String format(Date date, String pattern)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String formatLocalTime(long currentTimeMillis)
    {
        return DateFormatUtils.format(new Date(currentTimeMillis), patternLocalTime);
    }

    public static String formatLocalTime(Date date)
    {
        return DateFormatUtils.format(date, patternLocalTime);
    }

    public static String formatLocalTimeSimple(long currentTimeMillis)
    {
        return DateFormatUtils.format(new Date(currentTimeMillis), patternLocalTimeSimple);
    }

    public static String formatLocalTimeSimple(Date date)
    {
        return DateFormatUtils.format(date, patternLocalTimeSimple);
    }

    public static String formatUTC(long currentTimeMillis)
    {
        return DateFormatUtils.formatUTC(new Date(currentTimeMillis), patternUTC);
    }

    public static String formatUTC(Date date)
    {
        return DateFormatUtils.formatUTC(date, patternUTC);
    }

    public static Date parse(String str, String pattern)
    {
        try
        {
            return DateUtils.parseDate(str, pattern);
        }
        catch (ParseException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    public static Date parseLocalTime(String str)
    {
        try
        {
            return DateUtils.parseDate(str, patternLocalTime);
        }
        catch (ParseException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    public static Date parseLocalTimeSimple(String str)
    {
        try
        {
            return DateUtils.parseDate(str, patternLocalTimeSimple);
        }
        catch (ParseException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    public static Date parseUTC(String str)
    {
        try
        {
            str = StringUtils.replaceOnce(str, "Z", "+00:00");
            return DateUtils.parseDate(str, patternLocalTime);
        }
        catch (ParseException e)
        {
            throw ExceptionUtil.unchecked(e);
        }
    }

    public static void main(String[] args)
    {
        long currentTimeMillis = System.currentTimeMillis();

        String formatLocalTime = DateFormatUtil.formatLocalTime(currentTimeMillis);
        String formatLocalTimeSimple = DateFormatUtil.formatLocalTimeSimple(currentTimeMillis);
        String formatUTC = DateFormatUtil.formatUTC(currentTimeMillis);

        System.out.println("format:");
        System.out.println(formatLocalTime);
        System.out.println(formatLocalTimeSimple);
        System.out.println(formatUTC);

        Date parseLocalTime = DateFormatUtil.parseLocalTime(formatLocalTime);
        Date parseLocalTimeSimple = DateFormatUtil.parseLocalTimeSimple(formatLocalTimeSimple);
        Date parseUTC = DateFormatUtil.parseUTC(formatUTC);

        formatLocalTime = DateFormatUtil.formatLocalTime(parseLocalTime);
        formatLocalTimeSimple = DateFormatUtil.formatLocalTimeSimple(parseLocalTimeSimple);
        formatUTC = DateFormatUtil.formatUTC(parseUTC);

        System.out.println("parse:");
        System.out.println(formatLocalTime);
        System.out.println(formatLocalTimeSimple);
        System.out.println(formatUTC);

        System.out.println();
    }
}
