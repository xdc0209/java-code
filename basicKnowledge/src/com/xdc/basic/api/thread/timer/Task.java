package com.xdc.basic.api.thread.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Task
{
    private static Logger            log              = LoggerFactory.getLogger(Task.class);

    private static SimpleDateFormat  hourMinuteFormat = new SimpleDateFormat("hh:mm");

    private TaskFile                 taskFile         = null;

    private ScheduledFuture<?>       taskFuture       = null;

    private ScheduledFuture<?>       cancelFuture     = null;

    private ScheduledExecutorService exec             = null;

    public Task(ScheduledExecutorService exec, TaskFile taskFile)
    {
        super();
        this.taskFile = taskFile;
        this.exec = exec;
    }

    public TaskFile getTaskFile()
    {
        return taskFile;
    }

    public void setTaskFile(TaskFile taskFile)
    {
        this.taskFile = taskFile;
    }

    public synchronized void start() throws ParseException
    {
        if (taskFuture != null)
        {
            log.info("Task is Scheduled, no need to start. taskFile=[{}].", taskFile);
            return;
        }

        log.info("Schedule task start. taskFile=[{}].", taskFile);

        // 获取当前时间，并进行处理，只保留小时、分钟
        Date nowDate = new Date();
        String nowHourMinute = hourMinuteFormat.format(nowDate);
        Date nowHourMinuteDate = hourMinuteFormat.parse(nowHourMinute);

        String startTime = taskFile.getStartTime();
        String stopTime = taskFile.getStopTime();

        long startDelay = -1;
        long stopDelay = -1;

        // ["", "");
        if (StringUtils.isBlank(startTime) && StringUtils.isBlank(stopTime))
        {
            startDelay = 0;
            stopDelay = -1;
        }

        // ["", "09:50")
        else if (StringUtils.isBlank(startTime))
        {
            Date stopTimeDate = hourMinuteFormat.parse(stopTime);

            startDelay = 0;
            stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
            if (stopDelay <= 0)
            {
                stopDelay += stopDelay + 24 * 60 * 60 * 1000;
            }
        }

        // ["08:30", "")
        else if (StringUtils.isBlank(stopTime))
        {
            Date startTimeDate = hourMinuteFormat.parse(startTime);

            startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime();
            if (startDelay < 0)
            {
                startDelay = 0;
            }
            stopDelay = -1;
        }
        else if (!StringUtils.isBlank(startTime) && !StringUtils.isBlank(stopTime))
        {
            Date startTimeDate = hourMinuteFormat.parse(startTime);
            Date stopTimeDate = hourMinuteFormat.parse(stopTime);

            // ["08:30", "09:50")
            if (stopTimeDate.compareTo(startTimeDate) > 0)
            {
                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() > 0)
                {
                    startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime();
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
                }

                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0
                        && stopTimeDate.getTime() - nowHourMinuteDate.getTime() > 0)
                {
                    startDelay = 0;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
                }

                if (stopTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0)
                {
                    startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime() + 24 * 60 * 60 * 1000;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime() + 24 * 60 * 60 * 1000;
                }
            }

            // ["08:30", "08:30")
            else if (stopTimeDate.compareTo(startTimeDate) == 0)
            {
                // 不能发生，前面以校验
            }

            // ["08:30", "07:50"), 可以理解为****(07:50--[08:00****, 其中****理解为生效时间，--理解为不生效时间
            else if (stopTimeDate.compareTo(startTimeDate) < 0)
            {
                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() > 0
                        && stopTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0)
                {
                    startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime();
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime() + 24 * 60 * 60 * 1000;
                }

                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0)
                {
                    startDelay = 0;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime() + 24 * 60 * 60 * 1000;
                }

                if (stopTimeDate.getTime() - nowHourMinuteDate.getTime() > 0)
                {
                    startDelay = 0;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
                }
            }
        }

        // 不可能发生
        else
        {
            return;
        }

        taskFuture = exec.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                // 只是答应任务信息，真实的情况应该是执行任务配置中对应的脚本
                log.info("Exec task. taskFile=[{}].", taskFile);
            }
        }, startDelay, taskFile.getSleepTime() * 60 * 1000, TimeUnit.MILLISECONDS);

        if (stopDelay >= 0)
        {
            cancelFuture = exec.schedule(new Runnable()
            {
                public void run()
                {
                    if (taskFuture != null)
                    {
                        taskFuture.cancel(false);
                        taskFuture = null;
                    }

                    if (taskFuture != null)
                    {
                        taskFuture = null;
                    }

                    try
                    {
                        start();
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, stopDelay, TimeUnit.MILLISECONDS);
        }

        log.info("Schedule task finish. taskFile={}, startDelay=[{}], stopDelay=[{}].", taskFile, startDelay,
                stopDelay);
    }

    public synchronized void stop()
    {
        if (cancelFuture != null)
        {
            cancelFuture.cancel(false);
            cancelFuture = null;
        }

        if (taskFuture != null)
        {
            taskFuture.cancel(false);
            taskFuture = null;
        }
    }
}
