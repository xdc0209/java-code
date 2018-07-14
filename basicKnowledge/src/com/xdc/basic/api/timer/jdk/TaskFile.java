package com.xdc.basic.api.timer.jdk;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TaskFile
{
    // 时间格式校验的正在表达式，格式为HH:MM
    private static String hourMinuteRegularExpression = "^([0-1][0-9]|2[0-3]):[0-5][0-9]$";

    // 任务名称
    private String        name;

    // 任务文件在系统中的路径，配置文件正无需设置此字段，系统在解析文件时会自动设置此字段
    private String        path;

    // StartTime 可选, 脚本开始运行的时间, 格式为HH:MM (Default: 空,表示立即启动, 参考注2)
    private String        startTime;

    // StopTime 可选, 脚本结束运行的时间, 格式为HH:MM (Default: 空,表示没有结束时间, 参考注2)
    private String        stopTime;

    // SleepTime 可选, 执行周期, 单位: 分钟 (Default: 3)
    private int           sleepTime                   = 3;

    // ScriptFile 必选, 脚本文件名或者全路径
    private String        scriptFile;

    public TaskFile()
    {
        super();
    }

    public TaskFile(String name, String startTime, String stopTime, int sleepTime, String scriptFile)
    {
        super();
        this.name = name;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.sleepTime = sleepTime;
        this.scriptFile = scriptFile;
    }

    public TaskFile(String startTime, String stopTime, int sleepTime, String scriptFile)
    {
        super();
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.sleepTime = sleepTime;
        this.scriptFile = scriptFile;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getStopTime()
    {
        return stopTime;
    }

    public void setStopTime(String stopTime)
    {
        this.stopTime = stopTime;
    }

    public int getSleepTime()
    {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime)
    {
        this.sleepTime = sleepTime;
    }

    public String getScriptFile()
    {
        return scriptFile;
    }

    public void setScriptFile(String scriptFile)
    {
        this.scriptFile = scriptFile;
    }

    public void checkAndTrim()
    {
        if (startTime != null)
        {
            startTime = startTime.trim();
        }

        if (stopTime != null)
        {
            stopTime = stopTime.trim();
        }

        if (StringUtils.isNotBlank(startTime) && !startTime.matches(hourMinuteRegularExpression))
        {
            throw new IllegalArgumentException(
                    String.format("Field startTime [ %s ] is not valid in task file [ %s ]. Valid format is HH:MM.",
                            startTime, path));
        }

        if (StringUtils.isNotBlank(stopTime) && !stopTime.matches(hourMinuteRegularExpression))
        {
            throw new IllegalArgumentException(String.format(
                    "Field stopTime [ %s ] is not valid in task file [ %s ]. Valid format is HH:MM.", stopTime, path));
        }

        if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(stopTime)
                && StringUtils.equals(startTime, stopTime))
        {
            throw new IllegalArgumentException(
                    String.format("Field startTime [ %s ] and endTime [ %s ] must be different in task file [ %s ].",
                            startTime, stopTime, path));
        }

        if (sleepTime < 1)
        {
            throw new IllegalArgumentException(String.format(
                    "Field sleepTime [ %s ] is not valid in task file [ %s ]. Field sleepTime must be greater than 1.",
                    sleepTime, path));
        }
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
