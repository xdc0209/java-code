package com.xdc.basic.tools.alarm;

import java.util.Calendar;
import java.util.List;

public class AlarmChecker
{
    private static volatile AlarmCheckerThread alarmCheckerThread = null;

    public static void start()
    {
        if (alarmCheckerThread != null)
        {
            return;
        }

        alarmCheckerThread = new AlarmCheckerThread();
        // alarmCheckerThread.setName("AlarmCheckerThread");
        alarmCheckerThread.start();
    }

    public static void stop()
    {
        if (alarmCheckerThread == null)
        {
            return;
        }

        alarmCheckerThread.setRunning(false);
        alarmCheckerThread = null;
    }

    private static class AlarmCheckerThread extends Thread
    {
        private volatile boolean running = true;

        public AlarmCheckerThread()
        {
            super("AlarmCheckerThread");
        }

        public boolean isRunning()
        {
            return running;
        }

        public void setRunning(boolean running)
        {
            this.running = running;
        }

        public void run()
        {
            while (isRunning())
            {
                try
                {
                    List<Alarm> alarms = AlarmManger.query();

                    Calendar now = Calendar.getInstance();
                    System.out.println("当前时间：" + now.getTime());
                    System.out.println("所有闹铃：" + alarms);

                    for (Alarm alarm : alarms)
                    {
                        if (alarm.isEnabled() && alarm.isMatchedTime(now))
                        {
                            System.out.println("生效的闹铃：" + alarm);
                            if (alarm.disableAfterRing())
                            {
                                alarm.setEnabled(false);
                            }
                        }
                    }

                    System.out.println("休眠一分钟...");
                    System.out.println();
                    Thread.sleep(60 * 1000L);
                }
                catch (Throwable e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
