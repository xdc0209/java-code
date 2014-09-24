package com.xdc.basic.api.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadLocalTest
{
    public static void main(String[] args)
    {
        Judge.prepare();
        new Player(1).start();
        new Player(2).start();
        new Player(3).start();
    }
}

class Judge
{
    public static int  MAX_VALUE = 10;
    private static int targetValue;

    public static void prepare()
    {
        Random random = new Random();
        targetValue = random.nextInt(MAX_VALUE) + 1;
    }

    public static boolean judge(int value)
    {
        return value == targetValue;
    }
}

class Player extends Thread
{
    private final int playerId;

    public Player(int playerId)
    {
        this.playerId = playerId;
    }

    @Override
    public void run()
    {
        boolean success = false;
        while (!success)
        {
            int value = Attempt.guess(Judge.MAX_VALUE);
            success = Judge.judge(value);
            System.out.println(String.format("Plyaer %s Attempts %s and %s", playerId, value, success ? "Success"
                    : "Failed"));
        }
        Attempt.review(String.format("[IFNO] Plyaer %s Completed by ", playerId));
    }
}

class Attempt
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
        }
        while (record.contains(value));
        record.save(value);
        return value;
    }

    public static void review(String info)
    {
        System.out.println(info + getRecord());
    }

    private static Record getRecord()
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

class Record
{

    private final List<Integer> attemptList = new ArrayList<Integer>(); ;

    public void save(int value)
    {
        attemptList.add(value);
    }

    public boolean contains(int value)
    {
        return attemptList.contains(value);
    }

    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(attemptList.size() + " Times: ");
        int count = 1;
        for (Integer attempt : attemptList)
        {
            buffer.append(attempt);
            if (count < attemptList.size())
            {
                buffer.append(", ");
                count++;
            }
        }
        return buffer.toString();
    }

}
