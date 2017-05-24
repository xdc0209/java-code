package com.xdc.basic.tools.alarm;

import java.util.Calendar;

import com.xdc.basic.tools.alarm.repeat.RepeatFactory;

public class Main
{
    public static void main(String[] args)
    {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE) + 1;

        Alarm alarmOnce = new Alarm();
        alarmOnce.setTime(hour, minute);
        alarmOnce.setEnabled(true);
        alarmOnce.setRepeat(RepeatFactory.newOnce());
        alarmOnce.setRing("义勇军进行曲");
        alarmOnce.setRemark("闹铃");
        AlarmManger.add(alarmOnce);

        Alarm alarmEveryDay = new Alarm();
        alarmEveryDay.setTime(hour, minute);
        alarmEveryDay.setEnabled(true);
        alarmEveryDay.setRepeat(RepeatFactory.newEveryDay());
        alarmEveryDay.setRing("义勇军进行曲");
        alarmEveryDay.setRemark("闹铃");
        AlarmManger.add(alarmEveryDay);

        Alarm alarmEveryWeek = new Alarm();
        alarmEveryWeek.setTime(hour, minute);
        alarmEveryWeek.setEnabled(true);
        alarmEveryWeek.setRepeat(RepeatFactory.newEveryWeek(1, 2, 3, 4, 5));
        alarmEveryWeek.setRing("义勇军进行曲");
        alarmEveryWeek.setRemark("闹铃");
        AlarmManger.add(alarmEveryWeek);

        Alarm alarmEveryMonth = new Alarm();
        alarmEveryMonth.setTime(hour, minute);
        alarmEveryMonth.setEnabled(true);
        alarmEveryMonth.setRepeat(RepeatFactory.newEveryMonth(1, 2, 3, 4, 5, 29, 30, 31));
        alarmEveryMonth.setRing("义勇军进行曲");
        alarmEveryMonth.setRemark("闹铃");
        AlarmManger.add(alarmEveryMonth);

        Alarm alarmNationalWorkday = new Alarm();
        alarmNationalWorkday.setTime(hour, minute);
        alarmNationalWorkday.setEnabled(true);
        alarmNationalWorkday.setRepeat(RepeatFactory.newNationalWorkday());
        alarmNationalWorkday.setRing("义勇军进行曲");
        alarmNationalWorkday.setRemark("闹铃");
        AlarmManger.add(alarmNationalWorkday);
    }
}
