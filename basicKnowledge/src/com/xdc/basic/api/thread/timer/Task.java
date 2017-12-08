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
    private static Logger            log                  = LoggerFactory.getLogger(Task.class);

    private static long              oneDayInMilliSeconds = 24 * 60 * 60 * 1000L;

    private TaskFile                 taskFile             = null;

    private ScheduledFuture<?>       taskFuture           = null;

    private ScheduledFuture<?>       cancelFuture         = null;

    private ScheduledExecutorService exec                 = null;

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

        // SimpleDateFormat是线程不安全的类，一般不要定义为static变量，每次使用要重新new一个。JDK8可以使用线程安全的DateTimeFormatter，以便重用。
        SimpleDateFormat hourMinuteFormat = new SimpleDateFormat("HH:mm");

        // 获取当前时间，并进行处理，只保留小时、分钟。
        Date nowDate = new Date();
        String nowHourMinute = hourMinuteFormat.format(nowDate);
        Date nowHourMinuteDate = hourMinuteFormat.parse(nowHourMinute);

        final String startTime = taskFile.getStartTime();
        final String stopTime = taskFile.getStopTime();

        long startDelay = -1;
        long stopDelay = -1;

        // 样例：["", "") 启动时间为空，则立即启动。停止时间为空，则不停止。
        if (StringUtils.isBlank(startTime) && StringUtils.isBlank(stopTime))
        {
            startDelay = 0;
            stopDelay = -1;
        }

        // 样例：["", "09:50") 启动时间为空，则立即启动。如果当前时间大于等于09:50，则第二天的09:50停止任务。
        else if (StringUtils.isBlank(startTime) && !StringUtils.isBlank(stopTime))
        {
            Date stopTimeDate = hourMinuteFormat.parse(stopTime);

            startDelay = 0;
            stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
            // 如果当前时间大于停止时间，此时stopDelay等于小于0，此种情况下应在第二天的停止时间停止任务，需要将停止延迟增加24小时。
            if (stopDelay <= 0)
            {
                stopDelay += oneDayInMilliSeconds;
            }
        }

        // 样例：["08:30", "") 启动时间为08:30。停止时间为空，则不停止。
        else if (!StringUtils.isBlank(startTime) && StringUtils.isBlank(stopTime))
        {
            Date startTimeDate = hourMinuteFormat.parse(startTime);

            startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime();
            // 如果当前时间大于启动时间，此时startDelay小于0，此种情况下应立即启动，需要将启动延迟置为0。
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

            // 样例：["08:30", "09:50") 启动时间为08:30，停止时间为09:50。
            if (stopTimeDate.compareTo(startTimeDate) > 0)
            {
                // 如果当前时间小于启动时间，则等到启动时间是在启动任务。
                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() > 0)
                {
                    startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime();
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
                }

                // 如果当前时间在两个时间之间，则立即启动任务，然后等到停止时间停止任务。
                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0
                        && stopTimeDate.getTime() - nowHourMinuteDate.getTime() > 0)
                {
                    startDelay = 0;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime();
                }

                // 如果当前时间大于等于停止时间，这任务从第二天算起。
                if (stopTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0)
                {
                    startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime() + oneDayInMilliSeconds;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime() + oneDayInMilliSeconds;
                }
            }

            // 样例：["08:30", "08:30") 此参数非法，读取文件时已经校验。
            else if (stopTimeDate.compareTo(startTimeDate) == 0)
            {
                // 不能发生，前面以校验
            }

            // 样例：["08:30", "07:50"), 可以理解为**(07:50##[08:00**, 其中**理解为生效时间，##理解为不生效时间。
            else if (stopTimeDate.compareTo(startTimeDate) < 0)
            {
                // 如果当前时间小于启动时间且大于等于停止时间，则等到启动时间启动任务，然后在第二天的停止时间停止任务。
                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() > 0
                        && stopTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0)
                {
                    startDelay = startTimeDate.getTime() - nowHourMinuteDate.getTime();
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime() + oneDayInMilliSeconds;
                }

                // 如果当前时间大于等于启动时间则立即启动任务，然后在第二天的停止时间停止任务。
                if (startTimeDate.getTime() - nowHourMinuteDate.getTime() <= 0)
                {
                    startDelay = 0;
                    stopDelay = stopTimeDate.getTime() - nowHourMinuteDate.getTime() + oneDayInMilliSeconds;
                }

                // 如果当前时间小于停止时间则立即启动任务，然后在停止时间停止任务。
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
                // 只是答应任务信息，真实的情况应该是执行任务配置中对应的脚本。
                log.info("Exec task. taskFile=[{}].", taskFile);
            }
        }, startDelay, taskFile.getSleepTime() * 60 * 1000, TimeUnit.MILLISECONDS);

        if (stopDelay >= 0)
        {
            cancelFuture = exec.schedule(new Runnable()
            {
                public void run()
                {
                    log.info("Re-schedule task start. taskFile=[{}].", taskFile);

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
                        // 如果startTime为空，且stopTime不为空，则只执行一次，不再重新调度任务。
                        if (StringUtils.isBlank(startTime) && !StringUtils.isBlank(stopTime))
                        {
                            log.info(
                                    "Re-schedule task finish. Due to startTime is not empty and stopTime is empty, skip to re-schedule task. taskFile=[{}].",
                                    taskFile);
                            return;
                        }

                        start();
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                    log.info("Re-schedule task finish. taskFile=[{}].", taskFile);
                }
            }, stopDelay, TimeUnit.MILLISECONDS);
        }

        log.info("Schedule task finish. taskFile=[{}], startDelay=[{}ms], stopDelay=[{}ms].", taskFile, startDelay,
                stopDelay);
    }

    public synchronized void stop()
    {
        log.info("Cancel task start. taskFile=[{}].", taskFile);

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

        log.info("Cancel task finish. taskFile=[{}].", taskFile);
    }
}
