package com.xdc.basic.api;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class CalendarTest
{
    /**
     * 计算10后的今天还要经过多少天（需考虑闰年的情况）
     */
    @Test
    public void daysOf10Years()
    {
        Date today = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.YEAR, 10);

        long days = (calendar.getTimeInMillis() - today.getTime()) / (1000 * 60 * 60 * 24);
        System.out.println(days);
    }
}
