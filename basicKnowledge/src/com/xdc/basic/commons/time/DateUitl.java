package com.xdc.basic.commons.time;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 参考：
 * http://www.cnblogs.com/springcsc/archive/2009/12/03/1616347.html
 * http://outofmemory.cn/code-snippet/979/Java-Calendar-Date-usage-summary
 */
public class DateUitl
{
    /**
     * 判断是否为月末最后一天。
     */
    public static boolean isLastDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return isLastDayOfMonth(calendar);
    }

    /**
     * 判断是否为月末最后一天。
     */
    public static boolean isLastDayOfMonth(Calendar calendar)
    {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int actualMaxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return dayOfMonth == actualMaxDayOfMonth;
    }

    /**
     * 判断是否为月末周六(华为月末周六上班，呵呵)。
     */
    public static boolean isLastSaturdayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return isLastSaturdayOfMonth(calendar);
    }

    /**
     * 判断是否为月末周六(华为月末周六上班，呵呵)。
     */
    public static boolean isLastSaturdayOfMonth(Calendar calendar)
    {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.SATURDAY)
        {
            return false;
        }

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int actualMaxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return dayOfMonth > actualMaxDayOfMonth - 7;
    }

    /**
     * Queries if the given date is in Daylight Saving Time in default time zone.
     */
    public boolean inDaylightTime(Date date)
    {
        return TimeZone.getDefault().inDaylightTime(date);
    }
}
