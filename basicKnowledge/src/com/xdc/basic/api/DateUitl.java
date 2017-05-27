
package com.xdc.basic.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

/**
 * 参考：
 * http://www.cnblogs.com/springcsc/archive/2009/12/03/1616347.html
 * http://outofmemory.cn/code-snippet/979/Java-Calendar-Date-usage-summary
 */
public class DateUitl
{
    /**
     * 判断是否为月末最后一天
     */
    public static boolean isLastDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return isLastDayOfMonth(calendar);
    }

    /**
     * 判断是否为月末最后一天
     */
    public static boolean isLastDayOfMonth(Calendar calendar)
    {
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int actualMaxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        return dayOfMonth == actualMaxDayOfMonth;
    }

    /**
     * 判断是否为月末周六(华为月末周六上班，呵呵)
     */
    public static boolean isLastSaturdayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return isLastSaturdayOfMonth(calendar);
    }

    /**
     * 判断是否为月末周六(华为月末周六上班，呵呵)
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
     * SimpleDateFormat是线程不安全的类，一般不要定义为static变量，每次使用要重新new一个。JDK8可以使用线程安全的DateTimeFormatter，以便重用。
     */
    public static String format(Date date, String pattern)
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args)
    {
        Assert.assertEquals(false, isLastDayOfMonth(new Date(2016 - 1900, 11 - 1, 29)));
        Assert.assertEquals(true, isLastDayOfMonth(new Date(2016 - 1900, 11 - 1, 30)));
        Assert.assertEquals(false, isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 1)));

        Assert.assertEquals(false, isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 23)));
        Assert.assertEquals(false, isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 24)));
        Assert.assertEquals(false, isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 25)));

        Assert.assertEquals(false, isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 30)));
        Assert.assertEquals(true, isLastDayOfMonth(new Date(2016 - 1900, 12 - 1, 31)));
        Assert.assertEquals(false, isLastDayOfMonth(new Date(2017 - 1900, 1 - 1, 1)));

        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2016 - 1900, 11 - 1, 25)));
        Assert.assertEquals(true, isLastSaturdayOfMonth(new Date(2016 - 1900, 11 - 1, 26)));
        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2016 - 1900, 11 - 1, 27)));

        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 23)));
        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 24)));
        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 25)));

        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 30)));
        Assert.assertEquals(true, isLastSaturdayOfMonth(new Date(2016 - 1900, 12 - 1, 31)));
        Assert.assertEquals(false, isLastSaturdayOfMonth(new Date(2017 - 1900, 1 - 1, 1)));
    }
}
