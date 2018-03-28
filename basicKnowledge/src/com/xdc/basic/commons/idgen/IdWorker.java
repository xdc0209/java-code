package com.xdc.basic.commons.idgen;

import java.util.Random;

/**
 * 说明：来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
 * 格式：64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 * 摘自：https://github.com/twitter/snowflake/blob/scala_28/src/main/scala/com/twitter/service/snowflake/IdWorker.scala
 */
public class IdWorker
{
    private final static long twepoch            = 1288834974657L;

    // 机器标识位数
    private final static long workerIdBits       = 5L;

    // 数据中心标识位数
    private final static long datacenterIdBits   = 5L;

    // 机器ID最大值
    private final static long maxWorkerId        = -1L ^ (-1L << workerIdBits);

    // 数据中心ID最大值
    private final static long maxDatacenterId    = -1L ^ (-1L << datacenterIdBits);

    // 毫秒内自增位
    private final static long sequenceBits       = 12L;

    // 机器ID偏左移12位
    private final static long workerIdShift      = sequenceBits;

    // 数据中心ID左移17位
    private final static long datacenterIdShift  = sequenceBits + workerIdBits;

    // 时间毫秒左移22位
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask       = -1L ^ (-1L << sequenceBits);

    private static long       lastTimestamp      = -1L;

    private long              sequence           = 0L;
    private final long        workerId;
    private final long        datacenterId;

    public IdWorker(long workerId, long datacenterId)
    {
        if (workerId > maxWorkerId || workerId < 0)
        {
            if (workerId == -1)
            {
                this.workerId = new Random().nextInt((int) maxWorkerId);
            }
            else
            {
                throw new IllegalArgumentException("worker Id can't be greater than %d or less than 0");
            }
        }
        else
        {
            this.workerId = workerId;
        }

        if (datacenterId > maxDatacenterId || datacenterId < 0)
        {
            if (datacenterId == -1)
            {
                this.datacenterId = new Random().nextInt((int) maxDatacenterId);
            }
            else
            {
                throw new IllegalArgumentException("datacenter Id can't be greater than %d or less than 0");
            }
        }
        else
        {
            this.datacenterId = datacenterId;
        }
    }

    public synchronized long nextId()
    {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp)
        {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for "
                    + (lastTimestamp - timestamp) + " milliseconds.");
        }

        if (lastTimestamp == timestamp)
        {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0)
            {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        else
        {
            sequence = 0;
        }
        lastTimestamp = timestamp;

        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp)
    {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp)
        {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen()
    {
        return System.currentTimeMillis();
    }
}
