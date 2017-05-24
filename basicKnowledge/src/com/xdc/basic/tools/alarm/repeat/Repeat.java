package com.xdc.basic.tools.alarm.repeat;

import java.util.Calendar;

public interface Repeat
{
    /**
     * 日期是否符合重复条件
     */
    boolean isMatchedDay(Calendar now);

    /**
     * 生效后是否关闭闹铃
     */
    boolean disableAfterRing();
}
