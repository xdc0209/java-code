package com.xdc.basic.tools.alarm.repeat;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class EveryWeek implements Repeat
{
    /**
     * 每周的哪几天应该响铃，合法值为：[1-7]
     */
    private Set<Integer> days = new HashSet<Integer>(7);

    public void addDay(int day)
    {
        days.add(day);
    }

    public void addDays(int... days)
    {
        for (int day : days)
        {
            addDay(day);
        }
    }

    public void removeDay(int day)
    {
        days.remove(day);
    }

    public void removeDays(int... days)
    {
        for (int day : days)
        {
            removeDay(day);
        }
    }

    public Set<Integer> getDays()
    {
        return Collections.unmodifiableSet(days);
    }

    @Override
    public boolean isMatchedDay(Calendar now)
    {
        return days.contains(now.get(Calendar.DAY_OF_WEEK));
    }

    @Override
    public boolean disableAfterRing()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
