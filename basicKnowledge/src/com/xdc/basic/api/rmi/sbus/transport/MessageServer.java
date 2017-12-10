package com.xdc.basic.api.rmi.sbus.transport;

public interface MessageServer
{
    public void start();

    public void stop();

    public void registerListener(MessageListener listener);

    public void unregisterListener(MessageListener listener);
}
