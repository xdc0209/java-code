package com.xdc.basic.tools.alarm.repeat;

public class RepeatFactory
{
    public static Once newOnce()
    {
        return new Once();
    }

    public static EveryDay newEveryDay()
    {
        return new EveryDay();
    }

    public static EveryWeek newEveryWeek(int... days)
    {
        EveryWeek everyWeek = new EveryWeek();
        everyWeek.addDays(days);
        return everyWeek;
    }

    public static EveryMonth newEveryMonth(int... days)
    {
        EveryMonth everyMonth = new EveryMonth();
        everyMonth.addDays(days);
        return everyMonth;
    }

    public static NationalWorkday newNationalWorkday()
    {
        return new NationalWorkday();
    }
}
