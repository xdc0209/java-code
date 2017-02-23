package com.xdc.basic.api.thread.threadlocal.demo2;

import java.util.ArrayList;
import java.util.List;

public class Record
{
    private final List<Integer> attemptList = new ArrayList<Integer>();

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
        return String.format("Atempt %s times: %s", attemptList.size(), attemptList);
    }
}