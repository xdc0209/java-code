package com.xdc.basic.api.thread.lockfree.engine;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.util.concurrent.ServiceManager.Listener;

class EngineCopyOnWriteArrayList
{
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public boolean addListener(Listener listener)
    {
        return listeners.add(listener);
    }

    @SuppressWarnings("unused")
    public void doXXX()
    {
        for (Listener listener : listeners)
        {
            // listener.handle();
        }
    }
}