package com.xdc.basic.tools.alarm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AlarmManger
{
    private static Map<String, Alarm> alarmMap = new LinkedHashMap<String, Alarm>();

    public static void add(Alarm alarm)
    {
        if (alarmMap.isEmpty())
        {
            AlarmChecker.start();
        }
        alarmMap.put(alarm.getId(), alarm);
    }

    public static void remove(String alarmId)
    {
        alarmMap.remove(alarmId);
        if (alarmMap.isEmpty())
        {
            AlarmChecker.stop();
        }
    }

    public static void modify(Alarm alarm)
    {
        if (alarmMap.containsKey(alarm.getId()))
        {
            alarmMap.put(alarm.getId(), alarm);
        }
    }

    public static List<Alarm> query()
    {
        ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
        for (Alarm alarm : alarmMap.values())
        {
            alarmList.add(alarm);
        }
        return alarmList;
    }
}
