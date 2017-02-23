package com.xdc.basic.api.time;

import org.apache.commons.lang3.time.DateFormatUtils;

public class W3CDateFormatUtils
{
    // 注：T代表后面跟着时间，Z代表UTC统一时间

    // Year:
    // YYYY (eg 1997)
    public static String Year                          = "yyyy";

    // Year and month:
    // YYYY-MM (eg 1997-07)
    public static String YearMonth                     = "yyyy-MM";

    // Complete date:
    // YYYY-MM-DD (eg 1997-07-16)
    public static String YearMonthDay                  = "yyyy-MM-dd";                 // 同 DateFormatUtils.ISO_DATE_FORMAT

    // Complete date plus hours and minutes:
    // YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
    public static String YearMonthDayHourMinTZD        = "yyyy-MM-dd'T'HH:mmZZ";

    // Complete date plus hours, minutes and seconds:
    // YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
    public static String YearMonthDayHourMinSecTZD     = "yyyy-MM-dd'T'HH:mm:ssZZ";    // 同 DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT

    // Complete date plus hours, minutes, seconds and a decimal fraction of a second
    // YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)
    public static String YearMonthDayHourMinSecFracTZD = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

    public static String formatDate(long currentTimeMillis)
    {
        return DateFormatUtils.format(currentTimeMillis, YearMonthDay);
    }

    public static String formatDateTime(long currentTimeMillis)
    {
        return DateFormatUtils.format(currentTimeMillis, YearMonthDayHourMinSecTZD);
    }

    public static String formatDateTimeWtihFrac(long currentTimeMillis)
    {
        return DateFormatUtils.format(currentTimeMillis, YearMonthDayHourMinSecFracTZD);
    }

    public static void main(String[] args)
    {
        long currentTimeMillis = System.currentTimeMillis();

        System.out.println(formatDate(currentTimeMillis));
        System.out.println(formatDateTime(currentTimeMillis));
        System.out.println(formatDateTimeWtihFrac(currentTimeMillis));
    }
}
