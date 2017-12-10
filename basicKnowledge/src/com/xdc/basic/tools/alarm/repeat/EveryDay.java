package com.xdc.basic.tools.alarm.repeat;

import java.util.Calendar;

public class EveryDay implements Repeat
{
    @Override
    public boolean isMatchedDay(Calendar now)
    {
        return true;
    }

    @Override
    public boolean disableAfterRing()
    {
        return false;
    }
}
