package com.xdc.basic.tools.alarm.repeat;

import java.util.Calendar;

public class NationalWorkday implements Repeat
{
    @Override
    public boolean isMatchedDay(Calendar now)
    {
        // 提前获取国家工作日(单纯地将周一到到周五算作工作日是不可取的，有些时候，国家会根据节日进行调整)
        // 根据工作日在判断
        return false;
    }

    @Override
    public boolean disableAfterRing()
    {
        return false;
    }
}
