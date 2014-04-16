package com.xdc.basic.api.thread.lockfree.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.ServiceManager.Listener;

class Engine
{
    private List<Listener> listeners = new ArrayList<Listener>();

    public boolean addListener(Listener listener)
    {
        synchronized (listeners)
        {
            return listeners.add(listener);
        }
    }

    @SuppressWarnings("unused")
    public void doXXX()
    {
        synchronized (listeners)
        {
            for (Listener listener : listeners)
            {
                // listener.handle();
            }
        }
    }
}