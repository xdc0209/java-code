package com.xdc.basic.api.thread.threadlocal.demo2;

import java.util.Random;

public class Attempt
{
    private static ThreadLocal<Record> history = new ThreadLocal<Record>();

    public static int guess(int maxValue)
    {
        Record record = getRecord();
        Random random = new Random();
        int value = 0;
        do
        {
            value = random.nextInt(maxValue) + 1;
        } while (record.contains(value));
        record.save(value);
        return value;
    }

    public static Record getRecord()
    {
        Record record = history.get();
        if (record == null)
        {
            record = new Record();
            history.set(record);
        }
        return record;
    }
}
