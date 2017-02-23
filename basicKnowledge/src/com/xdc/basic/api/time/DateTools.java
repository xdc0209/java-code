package com.xdc.basic.api.time;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateTools
{
    public static String patternLocalTime       = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"; // 同 DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT

    public static String patternLocalTimeSimple = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String patternUTC             = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

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

    public static Date parseLocalTime(String str)
    {
        try
        {
            return DateUtils.parseDate(str, patternLocalTime);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
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
            e.printStackTrace();
            return null;
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
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args)
    {
        long currentTimeMillis = System.currentTimeMillis();

        String formatLocalTime = DateTools.formatLocalTime(currentTimeMillis);
        String formatLocalTimeSimple = DateTools.formatLocalTimeSimple(currentTimeMillis);
        String formatUTC = DateTools.formatUTC(currentTimeMillis);

        System.out.println("format:");
        System.out.println(formatLocalTime);
        System.out.println(formatLocalTimeSimple);
        System.out.println(formatUTC);

        Date parseLocalTime = DateTools.parseLocalTime(formatLocalTime);
        Date parseLocalTimeSimple = DateTools.parseLocalTimeSimple(formatLocalTimeSimple);
        Date parseUTC = DateTools.parseUTC(formatUTC);

        formatLocalTime = DateTools.formatLocalTime(parseLocalTime);
        formatLocalTimeSimple = DateTools.formatLocalTimeSimple(parseLocalTimeSimple);
        formatUTC = DateTools.formatUTC(parseUTC);

        System.out.println("parse:");
        System.out.println(formatLocalTime);
        System.out.println(formatLocalTimeSimple);
        System.out.println(formatUTC);

        System.out.println();
    }
}
