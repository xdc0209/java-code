package com.xdc.basic.api.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeKnowledge
{
    /**
     * 这个方法改变了Calendar内部维持的long值，是个投机取巧的方法，算是时间使用上的反例，不推荐使用。
     */
    public static Date getUTCTime()
    {
        // 1、取得本地时间：
        final Calendar cal = Calendar.getInstance();
        System.out.println("1、本地时间：" + cal.getTime());
        System.out.println("1、本地时间：" + cal.getTimeInMillis());

        // 2、取得时间偏移量：
        final int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        System.out.println("2、时间偏移量：" + zoneOffset);

        // 3、取得夏令时差：
        final int dstOffset = cal.get(Calendar.DST_OFFSET);
        System.out.println("3、夏令时差：" + dstOffset);

        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        System.out.println("4、UTC时间：" + cal.getTime());
        System.out.println("4、UTC时间：" + cal.getTimeInMillis());

        return cal.getTime();
    }

    public static String utc2Local(String utcTime)
    {
        String utcTimePatten = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String localTimePatten = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ"; // jdk bug, 时区中小时和分钟中间不支持有冒号
        try
        {
            SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
            utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date utcDate = utcFormater.parse(utcTime);

            SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
            localFormater.setTimeZone(TimeZone.getDefault());
            String localTime = localFormater.format(utcDate.getTime());
            // 手工添加冒号，符合通用的iso格式
            localTime = localTime.replaceAll("([-+][0-9][0-9])([0-9][0-9])$", "$1:$2");

            return localTime;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public boolean inDaylightTime(Date date)
    {
        return TimeZone.getDefault().inDaylightTime(date);
    }
}
