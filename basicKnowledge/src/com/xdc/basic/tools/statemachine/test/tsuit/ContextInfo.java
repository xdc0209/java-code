package com.xdc.basic.tools.statemachine.test.tsuit;

import java.util.ArrayList;
import java.util.List;

public class ContextInfo
{
    List<String> history = new ArrayList<String>();

    public List<String> getHistory()
    {
        return history;
    }

    public void addItem(String item)
    {
        history.add(item);
    }
}
